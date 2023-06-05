package com.yerayyas.marvelsuperheroes.domain.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comics(
    @SerializedName("available")
    val available: Int
): Parcelable