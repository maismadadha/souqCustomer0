package com.example.souqcustomer.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.souqcustomer.pojo.AddToCartRequest
import com.example.souqcustomer.pojo.AddToCartResponse
import com.example.souqcustomer.pojo.OrderDto
import com.example.souqcustomer.pojo.OrderItemDto
import com.example.souqcustomer.pojo.SimpleMessageResponse
import com.example.souqcustomer.pojo.UpdateCartItemRequest
import com.example.souqcustomer.retrofit.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderViewModel: ViewModel() {
    private val addToCartLiveData = MutableLiveData<AddToCartResponse>()
    private val errorLiveData = MutableLiveData<String>()
    private val conflictLiveData = MutableLiveData<Int>() // cart_id لو 409
    private val cartOrderLiveData = MutableLiveData<OrderDto?>()
    private val cartItemsLiveData = MutableLiveData<List<OrderItemDto>>()
    private val deleteCartLiveData = MutableLiveData<Boolean>()
    private val deleteItemLiveData = MutableLiveData<Boolean>()
    private val updateItemLiveData = MutableLiveData<Boolean>()
    private var currentCustomerId: Int? = null

    fun loadCustomerCart(customerId: Int) {
        currentCustomerId = customerId

        RetrofitInterface.api.getCustomerOrders(customerId)
            .enqueue(object : Callback<List<OrderDto>> {
                override fun onResponse(
                    call: Call<List<OrderDto>>,
                    response: Response<List<OrderDto>>
                ) {
                    if (response.isSuccessful) {
                        val orders = response.body() ?: emptyList()

                        // نفترض إن cart الوحيد هو اللي status = ON_CART
                        val cart = orders.firstOrNull { it.status == "ON_CART" }

                        cartOrderLiveData.value = cart
                        cartItemsLiveData.value = cart?.items ?: emptyList()
                    } else {
                        errorLiveData.value = "فشل في تحميل السلة"
                    }
                }

                override fun onFailure(call: Call<List<OrderDto>>, t: Throwable) {
                    errorLiveData.value = t.message ?: "مشكلة في الاتصال"
                }
            })
    }

    fun deleteCartItem(itemId: Int) {
        RetrofitInterface.api.deleteCartItem(itemId)
            .enqueue(object : Callback<SimpleMessageResponse> {
                override fun onResponse(
                    call: Call<SimpleMessageResponse>,
                    response: Response<SimpleMessageResponse>
                ) {
                    if (response.isSuccessful) {
                        // ✅ فقط نعيد تحميل السلة من جديد
                        currentCustomerId?.let { id ->
                            loadCustomerCart(id)
                        }
                    } else {
                        errorLiveData.value = "فشل حذف المنتج من السلة"
                    }
                }

                override fun onFailure(call: Call<SimpleMessageResponse>, t: Throwable) {
                    errorLiveData.value = t.message ?: "مشكلة في الاتصال"
                }
            })
    }

    fun addToCart(customerId: Int, storeId: Int, productId: Int, quantity: Int, price: Double) {
        val body = AddToCartRequest(
            customer_id = customerId,
            store_id = storeId,
            product_id = productId,
            quantity = quantity,
            price = price
        )
        RetrofitInterface.api.addToCart(body).enqueue(object : Callback<AddToCartResponse>{
            override fun onResponse(
                call: Call<AddToCartResponse?>,
                response: Response<AddToCartResponse?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    addToCartLiveData.value = response.body()
                } else {
                    if (response.code() == 409) {
                        val raw = response.errorBody()?.string()
                        var cartId=0
                        try {
                            val obj = org.json.JSONObject(raw ?: "")
                            cartId = obj.optInt("cart_id", 0)
                        } catch (_: Exception) { }

                        if (cartId != null && cartId != 0) {
                            conflictLiveData.value = cartId
                        } else {
                            errorLiveData.value = "لديك طلب مفتوح من متجر آخر"
                        }
                    } else {
                        errorLiveData.value = "فشل إضافة المنتج إلى السلة"
                    }
                }
            }

            override fun onFailure(
                call: Call<AddToCartResponse?>,
                t: Throwable
            ) {
                errorLiveData.value = t.message ?: "مشكلة في الاتصال"
            }

        })

    }

    fun updateCartItemQuantity(itemId: Int, newQuantity: Int) {
        // لو حابة تمنعي 0 أو أقل
        if (newQuantity < 1) return

        val body = UpdateCartItemRequest(quantity = newQuantity)

        RetrofitInterface.api.updateCartItem(itemId, body)
            .enqueue(object : Callback<AddToCartResponse> {
                override fun onResponse(
                    call: Call<AddToCartResponse>,
                    response: Response<AddToCartResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val order = response.body()!!.order
                        // هنا بنحدّث نفس الـ LiveData اللي بتستعمليها للسلة
                        cartOrderLiveData.value = order
                        cartItemsLiveData.value = order.items
                    } else {
                        errorLiveData.value = "فشل تحديث الكمية"
                    }
                }

                override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {
                    errorLiveData.value = t.message ?: "مشكلة في الاتصال"
                }
            })
    }

    fun deleteWholeCart(orderId: Int) {
        RetrofitInterface.api.deleteCart(orderId)
            .enqueue(object : Callback<SimpleMessageResponse> {
                override fun onResponse(
                    call: Call<SimpleMessageResponse>,
                    response: Response<SimpleMessageResponse>
                ) {
                    if (response.isSuccessful) {
                        // نفرّغ بيانات السلة من الـ LiveData
                        cartOrderLiveData.value = null
                        cartItemsLiveData.value = emptyList()
                    } else {
                        errorLiveData.value = "فشل حذف السلة"
                    }
                }

                override fun onFailure(call: Call<SimpleMessageResponse>, t: Throwable) {
                    errorLiveData.value = t.message ?: "مشكلة في الاتصال"
                }
            })
    }




    fun observeAddToCart() = addToCartLiveData
    fun observeError() = errorLiveData
    fun observeConflict() = conflictLiveData
    fun observeCartOrder() = cartOrderLiveData




    fun observeCartItems() = cartItemsLiveData
    fun observeDeleteCart() = deleteCartLiveData
    fun observeDeleteItem() = deleteItemLiveData
    fun observeUpdateItem() = updateItemLiveData

}