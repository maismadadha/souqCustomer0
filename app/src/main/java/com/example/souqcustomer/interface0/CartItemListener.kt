package com.example.souqcustomer.interface0

import com.example.souqcustomer.pojo.OrderItemDto

interface CartItemListener {
    fun onItemClick(productId: Int)
    fun onIncreaseQuantity(item: OrderItemDto)
    fun onDecreaseQuantity(item: OrderItemDto)
}
