package com.example.souqcustomer.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.souqcustomer.pojo.Categories2
import com.example.souqcustomer.pojo.Categories2Item
import com.example.souqcustomer.pojo.CategoriesItem
import com.example.souqcustomer.pojo.User
import com.example.souqcustomer.pojo.Users
import com.example.souqcustomer.retrofit.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class UserViewModel : ViewModel() {


    private val _categories = MutableLiveData<ArrayList<CategoriesItem>>()
    val categories: LiveData<ArrayList<CategoriesItem>> = _categories

    private val _categories2 = MutableLiveData<Categories2>()


    fun getCategories2() {
        RetrofitInterface.api.getCategories2().enqueue(object : Callback<Categories2> {
            override fun onResponse(
                call: Call<Categories2?>,
                response: Response<Categories2?>
            ) {
                if (response.isSuccessful) {
                    _categories2.value = response.body()
                }
            }

            override fun onFailure(
                call: Call<Categories2?>,
                t: Throwable
            ) {
                Log.d("UserViewModel", t.message.toString())
            }
        })

    }


    fun getCategories() {
        RetrofitInterface.api.getCategories().enqueue(object : Callback<ArrayList<CategoriesItem>> {
            override fun onResponse(
                call: Call<ArrayList<CategoriesItem>?>,
                response: Response<ArrayList<CategoriesItem>?>
            ) {
                if (response.isSuccessful) {
                    _categories.value = response.body()
                }
            }

            override fun onFailure(
                call: Call<ArrayList<CategoriesItem>?>,
                t: Throwable
            ) {
                Log.d("UserViewModel", t.message.toString())
            }
        })
    }


    fun getLiveCategories(): LiveData<ArrayList<CategoriesItem>> {
        return categories
    }

    fun getLiveCategories2(): LiveData<Categories2> {
        return _categories2
    }


}