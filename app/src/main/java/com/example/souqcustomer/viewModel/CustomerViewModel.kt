package com.example.souqcustomer.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.souqcustomer.pojo.CustomerProfile
import com.example.souqcustomer.pojo.CustomerProfileRequest
import com.example.souqcustomer.retrofit.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerViewModel : ViewModel() {
    private val customerLiveData = MutableLiveData<CustomerProfile>()
    private val errorLiveData = MutableLiveData<String>()

    fun getCustomerProfile(customerId: Int) {
        RetrofitInterface.api.getCustomerProfileById(customerId)
            .enqueue(object : Callback<CustomerProfile> {
                override fun onResponse(
                    call: Call<CustomerProfile?>,
                    response: Response<CustomerProfile?>
                ) {
                    if (response.isSuccessful) {
                        customerLiveData.value = response.body()
                    }
                }

                override fun onFailure(
                    call: Call<CustomerProfile?>,
                    t: Throwable
                ) {
                    errorLiveData.value = t.message

                }
            })
    }

    fun updateCustomerProfile(id: Int, body: CustomerProfileRequest) {
        RetrofitInterface.api.updateCustomerProfile(id, body).enqueue(object : Callback<CustomerProfile> {
            override fun onResponse(
                call: Call<CustomerProfile?>,
                response: Response<CustomerProfile?>
            ) {
                if (response.isSuccessful) {
                    customerLiveData.value = response.body()
                }
            }

            override fun onFailure(
                call: Call<CustomerProfile?>,
                t: Throwable
            ) {
                errorLiveData.value = t.message

            }

        })

    }

    fun observeCustomer(): MutableLiveData<CustomerProfile> = customerLiveData
    fun observeError(): MutableLiveData<String> = errorLiveData


}