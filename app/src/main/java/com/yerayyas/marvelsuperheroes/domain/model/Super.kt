package com.yerayyas.marvelsuperheroes.domain.model

import android.os.Parcelable
import com.yerayyas.marvelsuperheroes.data.model.Comics
import com.yerayyas.marvelsuperheroes.data.model.Superhero
import com.yerayyas.marvelsuperheroes.data.model.Thumbnail
import com.yerayyas.marvelsuperheroes.framework.data.database.entities.SuperheroEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Super(
    val id: Int,
    val comics: Comics,
    val description: String,
    val name: String,
    val thumbnail: Thumbnail
):Parcelable

fun Superhero.toDomain() = Super(id,comics, description, name, thumbnail)
fun SuperheroEntity.toDomain() = Super(id,comics, description, name, thumbnail)