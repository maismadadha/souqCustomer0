package com.example.souqcustomer.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.souqcustomer.adapters.ProductImagesAdapter
import com.example.souqcustomer.adapters.ProductOptionsAdapter
import com.example.souqcustomer.databinding.ActivityProductBinding
import com.example.souqcustomer.pojo.ProductOptionsItem
import com.example.souqcustomer.viewModel.OrderViewModel
import com.example.souqcustomer.viewModel.SellerViewModel


class ProductActivity : AppCompatActivity() {
    private var productId: Int = 0
    private var userId: Int = 0
    private var storeId: Int = 0
    private var priceValue: Double = 0.0
    private val customizations = mutableMapOf<String, String>()
    private lateinit var binding: ActivityProductBinding
    private lateinit var viewModel: SellerViewModel
    private lateinit var orderViewModel: OrderViewModel

    private var lastAddRequest: (() -> Unit)? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("souq_prefs", MODE_PRIVATE)
        userId = prefs.getInt("USER_ID", 0)
        productId = intent?.getIntExtra("productId", 0) ?: 0

        viewModel = ViewModelProvider(this)[SellerViewModel::class.java]
        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]

        val optionsAdapter = ProductOptionsAdapter { _, _ ->
            updateAddButtonState()
        }

        viewModel.getProductById(productId)
        observeProductByIdLiveData()

        viewModel.getProductImages(productId)
        observeProductImagesLiveData()

        viewModel.getProductOptions(productId)
        observeProductOptionsLiveData(optionsAdapter)

        binding.rvOptions.adapter = optionsAdapter
        binding.rvOptions.layoutManager = LinearLayoutManager(this)
        binding.rvOptions.itemAnimator = null

        observeAddToCartFlows()



        //add to cart
        binding.btnAddToCart.setOnClickListener {
            if (!binding.btnAddToCart.isEnabled) return@setOnClickListener

            if (userId == 0 || storeId == 0 ) {
                Toast.makeText(this, "خطأ في بيانات المستخدم أو المنتج", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // نجهز طلب الإضافة كـ lambda
            val request = {
                orderViewModel.addToCart(
                    customerId = userId,
                    storeId = storeId,
                    productId = productId,
                    quantity = 1,
                    price = priceValue,
                    customizations = if (customizations.isEmpty()) null else customizations
                )
            }

            // نخزّنه عشان نقدر نعيده لاحقًا
            lastAddRequest = request

            // وننفّذه أول مرة
            request()
        }


        //back
        binding.back.setOnClickListener {
            finish()
        }

    }

    private fun observeAddToCartFlows() {
        // نجاح إضافة للسلة
        orderViewModel.observeAddToCart().observe(this) { resp ->
            if (resp != null) {
                finish()
            }
        }

        // حالة كونفلكت: في كارت من متجر ثاني
        orderViewModel.observeCartConflict().observe(this) { cartId ->
            if (cartId == null || cartId == 0) return@observe

            android.app.AlertDialog.Builder(this)
                .setTitle("سلة سابقة")
                .setMessage("لديك سلة من متجر مختلف. هل ترغبين بحذفها والبدء بسلة جديدة؟")
                .setPositiveButton("متابعة وحذف") { _, _ ->
                    orderViewModel.deleteWholeCart(cartId) { ok ->
                        if (ok) {
                            // نعيد نفس طلب الإضافة اللي خزّناه
                            lastAddRequest?.invoke()
                        } else {
                            Toast.makeText(this, "فشل حذف السلة القديمة", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .setNegativeButton("لا", null)
                .show()
        }

        // لو حابة تتابعي الأخطاء
        orderViewModel.observeError().observe(this) { msg ->
            if (!msg.isNullOrEmpty()) {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }


    private var screenOptions: List<ProductOptionsItem> = emptyList()

    private fun observeProductOptionsLiveData(optionsAdapter: ProductOptionsAdapter) {
        viewModel.getLiveProductOptions().observe(this) { options ->
            screenOptions = options ?: emptyList()
            optionsAdapter.submitList(screenOptions)
            updateAddButtonState()
        }
    }

    private fun observeProductByIdLiveData() {
        viewModel.getLiveProductById().observe(this) { product ->
            if (product == null) return@observe

            binding.productName.text = product.name ?: ""
            binding.productName2.text = product.name ?: ""
            binding.productDescription.text = product.description ?: ""
            binding.productPrice.text = product.price

            storeId = product.store_id
            priceValue = product.price.toDoubleOrNull() ?: 0.0
        }
    }

    private fun observeProductImagesLiveData() {
        viewModel.getLiveProductImages().observe(this) { images ->
            val adapter = ProductImagesAdapter(images)
            binding.rvProductImages.adapter = adapter
            binding.rvProductImages.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(binding.rvProductImages)
            binding.indicator.attachToRecyclerView(binding.rvProductImages, snapHelper)
            adapter.registerAdapterDataObserver(binding.indicator.adapterDataObserver)
        }
    }

    private fun updateAddButtonState() {
        val optionsAdapter = binding.rvOptions.adapter as? ProductOptionsAdapter
        val selection = optionsAdapter?.currentSelection().orEmpty()
        customizations.clear()
        selection.forEach { (optionId, valueId) ->
            val option = screenOptions.find { it.id == optionId }
            val selectedValue = option?.values?.find { it.id == valueId }
            if (selectedValue != null && option != null) {
                customizations[option.name] = selectedValue.label
            }
        }

    }


}