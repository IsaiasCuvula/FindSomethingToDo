package com.bersyte.findsomethingtodo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bersyte.findsomethingtodo.models.RandomActivity
import com.bersyte.findsomethingtodo.network.RandomActivityAPIService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class RandomActivityViewModel : ViewModel() {

    private val randomActivityAPIService = RandomActivityAPIService()
    private val compositeDisposable = CompositeDisposable()


    val loadRandomActivity = MutableLiveData<Boolean>()
    val randomActivityResponse = MutableLiveData<RandomActivity>()
    val randomActivityLoadingError = MutableLiveData<Boolean>()

    fun getRandomActivityFromAPI() {
        loadRandomActivity.value = true
        compositeDisposable.add(
            randomActivityAPIService.getRandomActivity()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                    object : DisposableSingleObserver<RandomActivity>() {
                        override fun onSuccess(value: RandomActivity?) {
                            loadRandomActivity.value = false
                            randomActivityResponse.value = value
                            randomActivityLoadingError.value = false
                        }

                        override fun onError(e: Throwable?) {
                            loadRandomActivity.value = false
                            randomActivityLoadingError.value = false
                            e!!.printStackTrace()
                        }

                    })
        )
    }
}