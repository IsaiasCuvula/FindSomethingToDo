package com.bersyte.findsomethingtodo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bersyte.findsomethingtodo.repository.FavActivityRepository

@Suppress("UNCHECKED_CAST")
class FavoriteActivityViewModelFactory(
    private val repository: FavActivityRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteActivityViewModel::class.java)) {
           return FavoriteActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}