package com.bersyte.findsomethingtodo.network

import com.bersyte.findsomethingtodo.utils.Constants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RandomActivityAPIService {

    private val api = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(RandomActivityAPI::class.java)

    fun getRandomActivity() = api.getRandomActivity()
}