package com.yerayyas.marvelsuperheroes.framework.utils.type_converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yerayyas.marvelsuperheroes.data.model.Comics
import com.yerayyas.marvelsuperheroes.data.model.Thumbnail

object Converters {
    @TypeConverter
    @JvmStatic
    fun fromComics(value: Comics): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    @JvmStatic
    fun toComics(value: String): Comics {
        val type = object : TypeToken<Comics>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    @JvmStatic
    fun fromThumbnail(value: Thumbnail): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    @JvmStatic
    fun toThumbnail(value: String): Thumbnail {
        val type = object : TypeToken<Thumbnail>() {}.type
        return Gson().fromJson(value, type)
    }
}