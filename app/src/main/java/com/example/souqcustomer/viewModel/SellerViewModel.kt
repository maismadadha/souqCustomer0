package com.example.souqcustomer.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.souqcustomer.pojo.Product
import com.example.souqcustomer.pojo.ProductImages
import com.example.souqcustomer.pojo.Products
import com.example.souqcustomer.pojo.SellerCategories
import com.example.souqcustomer.pojo.SellersItem
import com.example.souqcustomer.retrofit.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerViewModel: ViewModel() {

    private val sellerById= MutableLiveData<SellersItem>()
    private val sellerCategories= MutableLiveData<SellerCategories>()
    private val categoryProducts= MutableLiveData<Products>()
    private val productImages= MutableLiveData<ProductImages>()
    private val productById= MutableLiveData<Product>()



    fun getSellerById(id: Int){
        RetrofitInterface.api.getSellersById(id).enqueue(object : Callback<SellersItem>{
            override fun onResponse(
                call: Call<SellersItem>,
                response: Response<SellersItem>
            ) {
                if (response.isSuccessful)
                    sellerById.value=response.body()
            }

            override fun onFailure(
                call: Call<SellersItem>,
                t: Throwable
            ) {
                Log.e("SellerByIdVM", t.message.toString())
            }
        })
    }
    fun getSellerCategories(id:Int){
        RetrofitInterface.api.getSellerCategories(id).enqueue(object : Callback<SellerCategories>{
            override fun onResponse(
                call: Call<SellerCategories>,
                response: Response<SellerCategories>
            ) {
                if(response.isSuccessful)
                    sellerCategories.value=response.body()
            }

            override fun onFailure(
                call: Call<SellerCategories>,
                t: Throwable
            ) {
                Log.e("SellerCategories", t.message.toString())
            }
        })
    }
    fun getCategoryProducts(id: Int){
        RetrofitInterface.api.getCategoryProducts(id).enqueue(object : Callback<Products>{
            override fun onResponse(
                call: Call<Products?>,
                response: Response<Products?>
            ) {
                if(response.isSuccessful)
                    categoryProducts.value=response.body()
            }

            override fun onFailure(
                call: Call<Products?>,
                t: Throwable
            ) {
                Log.e("CategoryProducts", t.message.toString())
            }
        })
    }
    fun getProductImages(id: Int){
        RetrofitInterface.api.getProductImages(id).enqueue(object : Callback<ProductImages>{
            override fun onResponse(
                call: Call<ProductImages>,
                response: Response<ProductImages>
            ) {
                if (response.isSuccessful)
                    productImages.value=response.body()
            }

            override fun onFailure(
                call: Call<ProductImages>,
                t: Throwable
            ) {
                Log.e("ProductImages", t.message.toString())
            }
        })
    }
    fun getProductById(id: Int){
        RetrofitInterface.api.getProductById(id).enqueue(object : Callback<Product>{
            override fun onResponse(
                call: Call<Product>,
                response: Response<Product>
            ) {
                if(response.isSuccessful)
                    productById.value=response.body()
            }

            override fun onFailure(
                call: Call<Product>,
                t: Throwable
            ) {
                Log.e("ProductById", t.message.toString())
            }
        })
    }


    fun getLiveSellerById(): LiveData<SellersItem>{
        return sellerById
    }
    fun getLiveSellerCategories(): LiveData<SellerCategories>{
        return sellerCategories
    }
    fun getLiveCategoryProducts(): LiveData<Products>{
        return categoryProducts
    }
    fun getLivePriductImages(): LiveData<ProductImages>{
        return productImages
    }
    fun getLiveProductById(): LiveData<Product>{
        return productById
    }
}