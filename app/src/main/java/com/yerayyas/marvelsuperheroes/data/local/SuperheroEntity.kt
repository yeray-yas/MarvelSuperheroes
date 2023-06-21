package com.yerayyas.marvelsuperheroes.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yerayyas.marvelsuperheroes.domain.model.Comics
import com.yerayyas.marvelsuperheroes.domain.model.Thumbnail

@Entity(tableName = "superheroes_table")
data class SuperheroEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "thumbnail") val thumbnail: Thumbnail,
    @ColumnInfo(name = "comics") val comics: Comics
)
