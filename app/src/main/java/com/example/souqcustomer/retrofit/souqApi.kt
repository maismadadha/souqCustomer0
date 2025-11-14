package com.example.souqcustomer.retrofit

import com.example.souqcustomer.pojo.AllProducts
import com.example.souqcustomer.pojo.Categories2
import com.example.souqcustomer.pojo.CreateCustomerRequest
import com.example.souqcustomer.pojo.CreateCustomerResponse
import com.example.souqcustomer.pojo.LoginRequest
import com.example.souqcustomer.pojo.LoginResponse
import com.example.souqcustomer.pojo.Product
import com.example.souqcustomer.pojo.ProductImages
import com.example.souqcustomer.pojo.ProductOptions
import com.example.souqcustomer.pojo.Products
import com.example.souqcustomer.pojo.SellerCategories
import com.example.souqcustomer.pojo.Sellers
import com.example.souqcustomer.pojo.SellersItem
import com.example.souqcustomer.pojo.SliderAds
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface souqApi {


    @GET("categories")
    fun getCategories2(): Call<Categories2>



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

    @GET("seller-profiles/{id}")
    fun getSellersById(@Path("id") id: Int): Call<SellersItem>

    @GET("stores/{id}/categories")
    fun getSellerCategories(@Path("id") id: Int): Call<SellerCategories>

    @GET("store-categories/{id}/products")
    fun getCategoryProducts(@Path("id") id: Int): Call<Products>

    @GET("products/{id}/images")
    fun getProductImages(@Path("id")id: Int): Call<ProductImages>

    @GET("products/{id}")
    fun getProductById(@Path("id")id: Int): Call<Product>

    @GET("products/{id}/options")
    fun getProductOptions(@Path("id")id: Int): Call<ProductOptions>

    @GET("seller-profiles")
    fun getSellerByCategoryId(
        @Query("main_category_id") id: Int
    ): Call<Sellers>

    @GET("users/{id}/favorites")
    fun getFavoritesSellersByUserId(@Path("id") id: Int): Call<Sellers>






}