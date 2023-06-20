package com.yerayyas.marvelsuperheroes.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.yerayyas.marvelsuperheroes.data.model.Comics

class ComicsConverter {
    @TypeConverter
    fun fromComics(comics: Comics): String {
        // Lógica para convertir `Comics` a una representación de cadena
        // que pueda ser almacenada en la base de datos (por ejemplo, JSON)
        val comicsJson = Gson().toJson(comics)
        return comicsJson
    }

    @TypeConverter
    fun toComics(comicsJson: String): Comics {
        // Lógica para convertir la representación de cadena almacenada en la
        // base de datos (por ejemplo, JSON) a un objeto `Comics`
        val comics = Gson().fromJson(comicsJson, Comics::class.java)
        return comics
    }
}
