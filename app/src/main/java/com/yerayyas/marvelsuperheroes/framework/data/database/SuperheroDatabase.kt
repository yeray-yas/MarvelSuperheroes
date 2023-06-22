package com.yerayyas.marvelsuperheroes.framework.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yerayyas.marvelsuperheroes.framework.data.database.dao.SuperheroDao
import com.yerayyas.marvelsuperheroes.framework.data.database.entities.SuperheroEntity
import com.yerayyas.marvelsuperheroes.framework.utils.type_converters.Converters

@Database(entities = [SuperheroEntity::class],
    version = 1)
@TypeConverters(Converters::class)
abstract class SuperheroDatabase:RoomDatabase() {

    abstract fun getSuperheroDao():SuperheroDao
}