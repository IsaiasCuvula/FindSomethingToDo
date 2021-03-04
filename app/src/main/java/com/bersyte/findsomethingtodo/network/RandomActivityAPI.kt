package com.bersyte.findsomethingtodo.network

import com.bersyte.findsomethingtodo.models.RandomActivity
import com.bersyte.findsomethingtodo.utils.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface RandomActivityAPI {

    @GET(Constants.API_ENDPOINT)
    fun getRandomActivity(): Single<RandomActivity>
}