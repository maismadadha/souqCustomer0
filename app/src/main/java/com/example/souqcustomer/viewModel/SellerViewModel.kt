package com.example.souqcustomer.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.souqcustomer.pojo.SellerCategories
import com.example.souqcustomer.pojo.SellersItem
import com.example.souqcustomer.retrofit.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerViewModel: ViewModel() {

    private val sellerById= MutableLiveData<SellersItem>()
    private val sellerCategories= MutableLiveData<SellerCategories>()



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
    fun getLiveSellerById(): LiveData<SellersItem>{
        return sellerById
    }

    fun getLiveSellerCategories(): LiveData<SellerCategories>{
        return sellerCategories
    }
}