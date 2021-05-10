package com.target.targetcasestudy.data

import retrofit2.Response
import retrofit2.http.GET

interface DealsWebService {
    /**
     * Fetch deals from server
     */
    @GET("deals")
    suspend fun deals(): Response<Products>
}