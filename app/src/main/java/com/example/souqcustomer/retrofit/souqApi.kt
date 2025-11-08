package com.example.souqcustomer.retrofit

import com.example.souqcustomer.pojo.Categories2
import com.example.souqcustomer.pojo.Categories2Item
import com.example.souqcustomer.pojo.CategoriesItem
import com.example.souqcustomer.pojo.User
import com.example.souqcustomer.pojo.Users
import retrofit2.Call
import retrofit2.http.GET

interface souqApi {
    @GET("users")
    fun getUsers(): Call<List<User>>

    @GET ("categories")
    fun getCategories():Call<ArrayList<CategoriesItem>>

    @GET ("categories")
    fun getCategories2():Call<Categories2>

}