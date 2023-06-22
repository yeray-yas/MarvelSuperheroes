package com.yerayyas.marvelsuperheroes.framework.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yerayyas.marvelsuperheroes.framework.data.database.dao.SuperheroDao
import com.yerayyas.marvelsuperheroes.framework.data.database.entities.SuperheroEntity

@Database(entities = [SuperheroEntity::class],
version = 1)
abstract class SuperheroDatabase:RoomDatabase() {
    abstract fun getSuperheroDao():SuperheroDao
}