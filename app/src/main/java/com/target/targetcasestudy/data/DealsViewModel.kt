package com.target.targetcasestudy.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.target.targetcasestudy.utils.NetworkResponse
import kotlinx.coroutines.launch

open class DealsViewModel(private val repository: DealsActivityRepository) : ViewModel() {

    /**
     * Fetch deal items to be displayed
     */
    fun deals() : LiveData<NetworkResponse<Products>> {
        val liveData = MutableLiveData<NetworkResponse<Products>>()
        viewModelScope.launch {
            liveData.postValue(repository.deals())
        }
        return liveData
    }
}