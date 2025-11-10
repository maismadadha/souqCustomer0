package com.example.souqcustomer.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.souqcustomer.pojo.Categories2
import com.example.souqcustomer.pojo.Sellers
import com.example.souqcustomer.pojo.SliderAds
import com.example.souqcustomer.retrofit.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class UserViewModel : ViewModel() {


    private val sliderAds= MutableLiveData<SliderAds>()
    private val _categories2 = MutableLiveData<Categories2>()
    private val sellers = MutableLiveData<Sellers>()


    fun getSliderAds(){
        RetrofitInterface.api.getSliderAds().enqueue(object : Callback<SliderAds>{
            override fun onResponse(
                call: Call<SliderAds?>,
                response: Response<SliderAds?>
            ) {
                if (response.isSuccessful)
                    sliderAds.value=response.body()
            }

            override fun onFailure(
                call: Call<SliderAds?>,
                t: Throwable
            ) {
                Log.d("SliderViewModel",t.message.toString())
            }
        })
    }
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

    fun getSellers(){
        RetrofitInterface.api.getSellers().enqueue(object : Callback<Sellers>{
            override fun onResponse(
                call: Call<Sellers?>,
                response: Response<Sellers?>
            ) {
                if(response.isSuccessful)
                    sellers.value=response.body()
            }

            override fun onFailure(
                call: Call<Sellers?>,
                t: Throwable
            ) {
                Log.d("sellerViewModel",t.message.toString())
            }
        })
    }


    fun getLiveSliderAds(): LiveData<SliderAds>{
        return sliderAds
    }
    fun getLiveCategories2(): LiveData<Categories2> {
        return _categories2
    }

    fun getLiveSellers(): LiveData<Sellers>{
        return sellers
    }



}