package com.example.souqcustomer.retrofit

import com.example.souqcustomer.pojo.AddToCartRequest
import com.example.souqcustomer.pojo.AddToCartResponse
import com.example.souqcustomer.pojo.AddressDto
import com.example.souqcustomer.pojo.AllProducts
import com.example.souqcustomer.pojo.Categories2
import com.example.souqcustomer.pojo.CreateCustomerRequest
import com.example.souqcustomer.pojo.CreateCustomerResponse
import com.example.souqcustomer.pojo.FavoriteStores
import com.example.souqcustomer.pojo.LoginRequest
import com.example.souqcustomer.pojo.LoginResponse
import com.example.souqcustomer.pojo.OrderDto
import com.example.souqcustomer.pojo.Product
import com.example.souqcustomer.pojo.ProductImages
import com.example.souqcustomer.pojo.ProductOptions
import com.example.souqcustomer.pojo.Products
import com.example.souqcustomer.pojo.SellerCategories
import com.example.souqcustomer.pojo.Sellers
import com.example.souqcustomer.pojo.SellersItem
import com.example.souqcustomer.pojo.SetOrderAddressRequest
import com.example.souqcustomer.pojo.SetOrderAddressResponse
import com.example.souqcustomer.pojo.SimpleMessageResponse
import com.example.souqcustomer.pojo.SimpleResponse
import com.example.souqcustomer.pojo.SliderAds
import com.example.souqcustomer.pojo.UpdateCartItemRequest
import com.example.souqcustomer.pojo.UpdateOrderMetaRequest
import com.example.souqcustomer.pojo.UpdateOrderNoteRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
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
    fun getProductImages(@Path("id") id: Int): Call<ProductImages>

    @GET("products/{id}")
    fun getProductById(@Path("id") id: Int): Call<Product>

    @GET("products/{id}/options")
    fun getProductOptions(@Path("id") id: Int): Call<ProductOptions>

    @GET("seller-profiles")
    fun getSellerByCategoryId(
        @Query("main_category_id") id: Int
    ): Call<Sellers>

    @GET("users/{id}/favorites")
    fun getFavoritesSellersByUserId(@Path("id") id: Int): Call<FavoriteStores>

    @POST("orders/add-to-cart")
    fun addToCart(
        @Body body: AddToCartRequest
    ): Call<AddToCartResponse>

    @GET("orders/customer")
    fun getCustomerOrders(
        @Query("customer_id") customerId: Int
    ): Call<List<OrderDto>>

    @DELETE("orders/cart/{cartId}")
    fun deleteCart(
        @Path("cartId") cartId: Int
    ): Call<SimpleMessageResponse>

    @PUT("orders/cart/item/{itemId}")
    fun updateCartItem(
        @Path("itemId") itemId: Int,
        @Body body: UpdateCartItemRequest
    ): Call<AddToCartResponse>   // أو موديل بسيط فيه order

    @DELETE("order-items/{itemId}")
    fun deleteCartItem(@Path("itemId") itemId: Int): Call<SimpleMessageResponse>

    @PUT("orders/{orderId}/note")
    fun updateOrderNote(
        @Path("orderId") orderId: Int,
        @Body body: UpdateOrderNoteRequest
    ): Call<AddToCartResponse>

    @PUT("orders/{orderId}/confirm")
    fun confirmOrder(
        @Path("orderId") orderId: Int
    ): Call<AddToCartResponse>

    @GET("addresses")
    fun getUserAddresses(
        @Query("user_id") userId: Int
    ): Call<List<AddressDto>>

    @FormUrlEncoded
    @PUT("orders/{orderId}/address")
    fun setOrderAddress(
        @Path("orderId") orderId: Int,
        @Field("address_id") addressId: Int
    ): Call<SetOrderAddressResponse>


    @PUT("orders/{orderId}/meta")
    fun updateOrderMeta(
        @Path("orderId") orderId: Int,
        @Body body: UpdateOrderMetaRequest
    ): Call<AddToCartResponse>







}