package com.example.souqcustomer.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.souqcustomer.pojo.AddressRequest
import com.example.souqcustomer.pojo.AddressResponse
import com.example.souqcustomer.pojo.CustomerProfile
import com.example.souqcustomer.retrofit.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapViewModel: ViewModel() {

    private val AddressLiveData = MutableLiveData<AddressResponse>()

    fun sendAddressToApi(
        userId: Int,
        addressRequest: AddressRequest
    ){
        RetrofitInterface.api.addAddress(userId, addressRequest).enqueue(object : Callback<AddressResponse> {
            override fun onResponse(
                call: Call<AddressResponse?>,
                response: Response<AddressResponse?>
            ) {
                if (response.isSuccessful) {
                    AddressLiveData.value = response.body()
                }
            }

            override fun onFailure(
                call: Call<AddressResponse?>,
                t: Throwable
            ) {
                Log.d("TAG", t.message.toString())
            }
        })
    }

    fun AddressLiveData(): MutableLiveData<AddressResponse> = AddressLiveData

}