package com.example.souqcustomer.retrofit

import com.example.souqcustomer.pojo.AllProducts
import com.example.souqcustomer.pojo.Categories2
import com.example.souqcustomer.pojo.CreateCustomerRequest
import com.example.souqcustomer.pojo.CreateCustomerResponse
import com.example.souqcustomer.pojo.LoginRequest
import com.example.souqcustomer.pojo.LoginResponse
import com.example.souqcustomer.pojo.Sellers
import com.example.souqcustomer.pojo.SliderAds
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface souqApi {


    @GET ("categories")
    fun getCategories2():Call<Categories2>


    @POST("users")
    fun createUser(
        @Body request: CreateCustomerRequest
    ): Call<CreateCustomerResponse>


    @POST("app/login-user")
    fun loginUser(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @GET("slider-ads")
    fun getSliderAds(): Call<SliderAds>

    @GET("seller-profiles")
    fun getSellers(): Call<Sellers>

    @GET("products")
    fun getAllProducts(): Call<AllProducts>
}