package com.yerayyas.marvelsuperheroes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yerayyas.marvelsuperheroes.data.model.Comics

@Entity(tableName = "superheroes")
data class SuperheroEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val thumbnailUrl: String,
    val comics: Comics
    )
