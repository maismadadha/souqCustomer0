package com.example.souqcustomer.pojo

data class SliderAdsItem(
    val created_at: String,
    val end_date: String,
    val id: Int,
    val title: String?,
    val description: String?,
    val image_url: String,
    val start_date: String,
    val store: Store,
    val store_id: Int,
    val updated_at: String
)