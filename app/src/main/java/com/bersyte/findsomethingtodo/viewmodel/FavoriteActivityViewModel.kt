package com.bersyte.findsomethingtodo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bersyte.findsomethingtodo.models.RandomActivity
import com.bersyte.findsomethingtodo.repository.FavActivityRepository
import kotlinx.coroutines.launch

class FavoriteActivityViewModel(
    private val repository: FavActivityRepository
) : ViewModel() {


    fun insertFAvActivity(favActivity: RandomActivity) = viewModelScope.launch {
        repository.insertFavActivity(favActivity)
    }

    fun deleteFAvActivity(favActivity: RandomActivity) = viewModelScope.launch {
        repository.deleteFavActivity(favActivity)
    }

    val allFavActivities: LiveData<List<RandomActivity>> = repository.getAllFAvActivity.asLiveData()
}