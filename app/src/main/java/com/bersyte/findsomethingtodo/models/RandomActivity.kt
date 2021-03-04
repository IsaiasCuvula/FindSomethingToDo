package com.bersyte.findsomethingtodo.models


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "fav_activity_table")
data class RandomActivity(
    val accessibility: Double?,
    val activity: String?,
    val key: String?,
    val link: String?,
    val participants: Int?,
    val price: Double?,
    val type: String?
) : Parcelable{

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}