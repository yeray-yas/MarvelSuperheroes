package com.yerayyas.marvelsuperheroes.domain.model

import android.os.Parcelable
import com.yerayyas.marvelsuperheroes.data.local.SuperheroEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Superheroes(
    val comics: Comics,
    val description: String,
    val id: Int,
    val name: String,
    val thumbnail: Thumbnail
) : Parcelable

fun Superhero.toDomain() = Superheroes(
    comics, description, id,
    name, thumbnail
)

fun SuperheroEntity.toDomain() = Superheroes(
    comics, description, id,
    name, thumbnail
)