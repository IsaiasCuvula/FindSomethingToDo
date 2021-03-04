package com.bersyte.findsomethingtodo.db

import androidx.room.*
import com.bersyte.findsomethingtodo.models.RandomActivity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavActDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavAct(favAct: RandomActivity)

    @Delete
    suspend fun deleteFav(favAct: RandomActivity)

    @Query("SELECT * FROM fav_activity_table ORDER BY id DESC")
    fun getAllFavActivity(): Flow<List<RandomActivity>>


}