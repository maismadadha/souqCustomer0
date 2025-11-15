package com.example.souqcustomer.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.souqcustomer.R
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
    private lateinit var binding: ActivityProductBinding
    private lateinit var viewModel: SellerViewModel
    private lateinit var orderViewModel: OrderViewModel

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


        //add to cart
        binding.btnAddToCart.setOnClickListener {

            if (!binding.btnAddToCart.isEnabled) return@setOnClickListener

            if (userId == 0 || storeId == 0 || priceValue == 0.0) {
                Toast.makeText(this, "خطأ في بيانات المستخدم أو المنتج", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            orderViewModel.addToCart(
                customerId = userId,
                storeId = storeId,
                productId = productId,
                quantity = 1,          // لاحقاً ممكن نربطها بعدد من UI
                price = priceValue
            )
            finish()


        }

        //back
        binding.back.setOnClickListener {
            finish()
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

            binding.productName.text        = product.name ?: ""
            binding.productName2.text=product.name?:""
            binding.productDescription.text = product.description ?: ""
            binding.productPrice.text       = product.price

            storeId = product.store_id
            priceValue = product.price.toDoubleOrNull() ?: 0.0
        }
    }

    private fun observeProductImagesLiveData() {
        viewModel.getLiveProductImages().observe(this){images->
            val adapter= ProductImagesAdapter(images)
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
        val adapter = binding.rvOptions.adapter as? ProductOptionsAdapter ?: return
        val sel = adapter.currentSelection() // Map<optionId, valueId>
        val allRequiredSelected = screenOptions.all { opt ->
            opt.required == 0 || sel[opt.id] != null
        }
        binding.btnAddToCart.isEnabled = allRequiredSelected
    }

    private fun observeAddToCart() {
        orderViewModel.observeAddToCart().observe(this) { response ->
            if (response == null) return@observe
            Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
            // ممكن بعدها تروح على CartActivity لو حاب
            // startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private fun observeAddToCartError() {
        orderViewModel.observeError().observe(this) { msg ->
            if (msg.isNullOrEmpty()) return@observe
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeCartConflict() {
        orderViewModel.observeConflict().observe(this) { cartId ->
            if (cartId == 0) return@observe

            // حالياً بس نعرض رسالة
            Toast.makeText(
                this,
                "لديك سلة من متجر آخر (ID = $cartId) - رح نضبط حذفها لاحقاً",
                Toast.LENGTH_LONG
            ).show()

            // لاحقاً بنضيف Dialog يحذف cart القديم وينادي addToCart مرة ثانية
        }
    }


}