package com.murqdan.curhatanku.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "listStory")
@Parcelize
data class CurhatanResponse(
    @PrimaryKey
    @field:SerializedName("listStory")
    val listCurhatan: List<ListCurhatanItem>
) : Parcelable

@Entity(tableName = "listStory")
@Parcelize
data class ListCurhatanItem(
    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("photoUrl")
    val photoUrl: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("lon")
    val lon: Double,

    @field:SerializedName("lat")
    val lat: Double,
) : Parcelable
