package com.yerayyas.marvelsuperheroes.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.yerayyas.marvelsuperheroes.data.model.Item
import kotlinx.parcelize.Parcelize

@Parcelize
data class Series(
    @SerializedName("available")
    val available: Int,
    @SerializedName("collectionURI")
    val collectionURI: String,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("returned")
    val returned: Int
): Parcelable