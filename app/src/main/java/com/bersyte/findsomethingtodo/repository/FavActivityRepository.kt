package com.bersyte.findsomethingtodo.repository

import androidx.annotation.WorkerThread
import com.bersyte.findsomethingtodo.db.FavActDatabase
import com.bersyte.findsomethingtodo.models.RandomActivity
import kotlinx.coroutines.flow.Flow

class FavActivityRepository(private val db: FavActDatabase) {


    @WorkerThread
    suspend fun insertFavActivity(favActivity: RandomActivity) {
        db.favActDao().insertFavAct(favActivity)
    }

    @WorkerThread
    suspend fun deleteFavActivity(favActivity: RandomActivity) {

        db.favActDao().deleteFav(favActivity)
    }

    val getAllFAvActivity: Flow<List<RandomActivity>> =
        db.favActDao().getAllFavActivity()


}