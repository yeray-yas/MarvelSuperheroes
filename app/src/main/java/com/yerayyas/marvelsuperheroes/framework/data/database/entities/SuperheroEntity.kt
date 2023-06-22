package com.yerayyas.marvelsuperheroes.framework.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yerayyas.marvelsuperheroes.data.model.Comics
import com.yerayyas.marvelsuperheroes.data.model.Thumbnail
import com.yerayyas.marvelsuperheroes.domain.model.Super

@Entity(tableName = "superhero_table")
data class SuperheroEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "comics")
    val comics: Comics,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "thumbnail")
    val thumbnail: Thumbnail
)

fun Super.toDatabase() = SuperheroEntity(
    comics = comics, description = description,
    name = name, thumbnail = thumbnail
)
