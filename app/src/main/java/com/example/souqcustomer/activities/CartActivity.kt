package com.example.souqcustomer.activities

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.souqcustomer.R
import com.example.souqcustomer.adapters.CartItemsAdapter
import com.example.souqcustomer.databinding.ActivityCartBinding
import com.example.souqcustomer.interface0.CartItemListener
import com.example.souqcustomer.interface0.OnClick
import com.example.souqcustomer.pojo.OrderItemDto
import com.example.souqcustomer.viewModel.OrderViewModel

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var adapter: CartItemsAdapter

    private var currentOrderId: Int? = null
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("souq_prefs", MODE_PRIVATE)
        userId = prefs.getInt("USER_ID", 0)

        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]
        adapter = CartItemsAdapter(
            emptyList(),
            object : CartItemListener {
                override fun onItemClick(productId: Int) {
                    val intent = Intent(this@CartActivity, ProductActivity::class.java)
                    intent.putExtra("productId", productId)
                    startActivity(intent)
                }

                override fun onIncreaseQuantity(item: OrderItemDto) {
                    orderViewModel.updateCartItemQuantity(
                        itemId = item.id,
                        newQuantity = item.quantity
                    )
                }

                override fun onDecreaseQuantity(item: OrderItemDto) {
                    orderViewModel.updateCartItemQuantity(
                        itemId = item.id,
                        newQuantity = item.quantity
                    )
                }
            }
        )

        binding.rvCart.adapter = adapter
        val swipeToDeleteCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = adapter.getItemAt(position)

                showDeleteDialog(item)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val paint = Paint()

                // نحول dp → px
                val density = recyclerView.context.resources.displayMetrics.density
                val boxWidth = 70 * density        // عرض الصندوق الأحمر (30dp)
                val maxSwipe = 80 * density        // أقصى مسافة يتحرك فيها الآيتِم (80dp)

                if (dX > 0) { // سحب لليمين
                    paint.color = Color.parseColor("#D32F2F")

                    // نخلي الصندوق ثابت على اليسار وبعرض 30dp
                    val left = itemView.left.toFloat()
                    val right = left + boxWidth
                    val top = itemView.top.toFloat()
                    val bottom = itemView.bottom.toFloat()

                    c.drawRect(left, top, right, bottom, paint)

                    // أيقونة الحذف جوا الصندوق
                    val icon = ContextCompat.getDrawable(this@CartActivity, R.drawable.delete) ?: return
                    val iconWidth = icon.intrinsicWidth
                    val iconHeight = icon.intrinsicHeight

                    val iconLeft = (left + (boxWidth - iconWidth) / 2).toInt()
                    val iconTop = (top + (itemView.height - iconHeight) / 2).toInt()
                    val iconRight = iconLeft + iconWidth
                    val iconBottom = iconTop + iconHeight

                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    icon.draw(c)
                }

                // نخلي حركة الآيتِم نفسها محدودة بـ maxSwipe
                val clampedDX = dX.coerceIn(0f, maxSwipe)

                super.onChildDraw(c, recyclerView, viewHolder, clampedDX, dY, actionState, isCurrentlyActive)
            }



        }
        ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(binding.rvCart)
        binding.rvCart.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        orderViewModel.loadCustomerCart(userId)
        observeCartData()

        binding.deleteCartImage.setOnClickListener {
            if (currentOrderId == null) return@setOnClickListener

            AlertDialog.Builder(this)
                .setTitle("حذف السلة")
                .setMessage("هل أنت متأكد أنك تريد حذف السلة كاملة؟")
                .setPositiveButton("حذف") { _, _ ->
                    orderViewModel.deleteWholeCart(currentOrderId!!)
                }
                .setNegativeButton("إلغاء", null)
                .show()
        }

        binding.toPayButton.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra("orderId", currentOrderId)
            intent.putExtra("totalPrice", binding.totalPrice.text.toString().toDouble())
            intent.putExtra("note", binding.note.text.toString())
            startActivity(intent)
            finish()
        }

        binding.back.setOnClickListener {
            finish()
        }


    }

    private fun observeCartData() {

        // 1) الطلب نفسه: بس للأرقام (المجموع، id، اسم المتجر لاحقاً)
        orderViewModel.observeCartOrder().observe(this) { order ->
            if (order != null) {
                currentOrderId = order.id
                binding.totalPrice.text = "${order.total_price}"
                // هون لاحقاً منجيب اسم المتجر من API بدل store_id
                binding.storeName.text = order.store_name?:""
                val note=binding.note.text


            } else {
                currentOrderId = null
                binding.totalPrice.text = "0.0"
                binding.storeName.text = ""
            }
        }

        // 2) العناصر جوة السلة: هون نقرّر إذا السلة فاضية ولا لا
        orderViewModel.observeCartItems().observe(this) { items ->
            adapter.submitList(items)

            if (items.isEmpty()) {
                // سلة فاضية 👇
                binding.tvEmptyCart.visibility = View.VISIBLE
                binding.scrollContent.visibility = View.GONE   // نخفي اللستة و الملاحظة
                binding.linearLayout.visibility = View.GONE    // نخفي الإجمالي + زر الدفع
            } else {
                // في عناصر 👇
                binding.tvEmptyCart.visibility = View.GONE
                binding.scrollContent.visibility = View.VISIBLE
                binding.linearLayout.visibility = View.VISIBLE
            }
        }

        orderViewModel.observeError().observe(this) { msg ->
            if (!msg.isNullOrEmpty()) {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun showDeleteDialog(item: OrderItemDto) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("حذف المنتج")
            .setMessage("هل أنت متأكد أنك تريد حذف هذا المنتج من السلة؟")
            .setPositiveButton("حذف") { _, _ ->
                orderViewModel.deleteCartItem(item.id)  // بنعملها الآن 👇
            }
            .setNegativeButton("إلغاء") { dialog, _ ->
                dialog.dismiss()
                adapter.notifyDataSetChanged() // نرجّع السحب بدون حذف
            }
            .create()

        dialog.show()
    }


}





