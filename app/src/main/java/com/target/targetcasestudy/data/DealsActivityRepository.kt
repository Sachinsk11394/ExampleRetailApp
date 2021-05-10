package com.target.targetcasestudy.data

import com.target.targetcasestudy.utils.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class DealsActivityRepository(private val webService: DealsWebService,
                                   private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) {
    suspend fun deals(): NetworkResponse<Products> {
        return withContext(ioDispatcher){
            val response = webService.deals()
            if (response.isSuccessful) {
                return@withContext NetworkResponse.success(response.body()!!)
            } else {
                return@withContext NetworkResponse.failure<Products>(Throwable("Something went wrong, Please try again later."))
            }
        }
    }
}