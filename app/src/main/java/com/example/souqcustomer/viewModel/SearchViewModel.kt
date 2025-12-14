package com.example.souqcustomer.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.souqcustomer.pojo.SellersItem
import com.example.souqcustomer.retrofit.RetrofitInterface
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _results = MutableLiveData<List<SellersItem>>()
    val results: LiveData<List<SellersItem>> = _results

    fun search(query: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInterface.api.searchSellers(query)
                if (response.isSuccessful) {
                    _results.postValue(response.body() ?: emptyList())
                } else {
                    _results.postValue(emptyList())
                }
            } catch (e: Exception) {
                _results.postValue(emptyList())
            }
        }
    }
}
