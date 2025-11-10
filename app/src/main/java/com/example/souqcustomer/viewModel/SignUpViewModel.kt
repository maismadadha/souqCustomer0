package com.example.souqcustomer.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.souqcustomer.pojo.CreateCustomerRequest
import com.example.souqcustomer.pojo.CreateCustomerResponse
import com.example.souqcustomer.pojo.LoginRequest
import com.example.souqcustomer.pojo.LoginResponse
import com.example.souqcustomer.retrofit.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {

    private val signupLiveData = MutableLiveData<CreateCustomerResponse>()
    private val errorLiveData = MutableLiveData<String>()


    private val loginLiveData= MutableLiveData<LoginResponse>()
    private val errorLoginLiveData = MutableLiveData<String>()


    fun logIN(phone: String, )
    {
        val body = LoginRequest(
            phone= phone,
        )
        RetrofitInterface.api.loginUser(body).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    loginLiveData.value = response.body()
                    Log.d("LoginVM", "Success: ${response.body()}")
                } else {
                    val msg = response.errorBody()?.string() ?: "فشل تسجيل الدخول"
                    errorLoginLiveData.value = msg
                    Log.e("LoginVM", "API Error: $msg")
                }
            }

            override fun onFailure(
                call: Call<LoginResponse>,
                t: Throwable
            ) {
                val msg = t.message ?: "مشكلة في الاتصال"
                errorLoginLiveData.value = msg
                Log.e("LoginVM", "Failure: $msg")
            }
        })
    }
    fun signUp(
        phone: String,
        firstName: String,
        lastName: String,
        email: String?
    ) {
        val body = CreateCustomerRequest(
            phone = phone,
            email = email,
            first_name = firstName,
            last_name = lastName
            // role = "customer" تلقائي من الـdata class
        )

        RetrofitInterface.api.createUser(body)
            .enqueue(object : Callback<CreateCustomerResponse> {
                override fun onResponse(
                    call: Call<CreateCustomerResponse>,
                    response: Response<CreateCustomerResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        signupLiveData.value = response.body()
                        Log.d("SignUpVM", "Success: ${response.body()}")
                    } else {
                        val msg = response.errorBody()?.string() ?: "فشل إنشاء الحساب"
                        errorLiveData.value = msg
                    }
                }

                override fun onFailure(
                    call: Call<CreateCustomerResponse>,
                    t: Throwable
                ) {
                    errorLiveData.value = t.message ?: "مشكلة في الاتصال"
                }
            })
    }

    fun observeSignUpLiveData(): MutableLiveData<CreateCustomerResponse> = signupLiveData
    fun observeErrorLiveData(): MutableLiveData<String> = errorLiveData

    fun observeLoginLiveData(): MutableLiveData<LoginResponse> = loginLiveData
    fun observeErrorLoginLiveData(): MutableLiveData<String> = errorLoginLiveData

}
