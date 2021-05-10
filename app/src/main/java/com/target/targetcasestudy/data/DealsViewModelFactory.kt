package com.target.targetcasestudy.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DealsViewModelFactory(private val repository: DealsActivityRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DealsViewModel::class.java)) {
            return DealsViewModel(repository) as T
        } else {
            throw IllegalArgumentException("view model not available")
        }
    }
}