package com.yerayyas.marvelsuperheroes.data.local

import androidx.room.TypeConverter
import com.yerayyas.marvelsuperheroes.domain.model.Thumbnail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ThumbnailConverter {
    @TypeConverter
    fun fromString(value: String): Thumbnail {
        return Gson().fromJson(value, object : TypeToken<Thumbnail>() {}.type)
    }

    @TypeConverter
    fun toString(thumbnail: Thumbnail): String {
        return Gson().toJson(thumbnail)
    }
}
