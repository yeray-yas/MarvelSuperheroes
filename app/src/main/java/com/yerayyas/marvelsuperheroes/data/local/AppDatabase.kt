package com.yerayyas.marvelsuperheroes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [SuperheroEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ComicsConverter::class, ThumbnailConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun superheroDao(): SuperheroDao
}