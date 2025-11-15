package com.example.souqcustomer.pojo

data class AddToCartResponse(
    val message: String,
    val order: OrderDto
)

data class OrderDto(
    val id: Int,
    val customer_id: Int,
    val store_id: Int,
    val subtotal: Double,
    val total_price: Double,
    val items_count: Int,
    val status: String,
    val note: String?,          // 👈 عشان ملاحظة الطلب
    val store_name: String?,    // 👈 اسم المتجر الجاي من الباك
    val items: List<OrderItemDto>
)

data class OrderItemDto(
    val id: Int,
    val order_id: Int,
    val product_id: Int,
    val quantity: Int,
    val price: Double,
    val discount: Double,
    val product: ProductInCartDto?, // عشان الاسم والصورة
    val customizations: Map<String, String>?
)

data class ProductInCartDto(
    val id: Int,
    val name: String?,
    val description: String?,
    val main_image_url: String?
)
