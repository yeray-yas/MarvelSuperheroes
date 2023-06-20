package com.yerayyas.marvelsuperheroes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface SuperheroDao {
    @Query("SELECT * FROM superheroes")
    suspend fun getAllSuperheroes(): List<SuperheroEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSuperheroes(superheroes: List<SuperheroEntity>)

    @Query("DELETE FROM superheroes")
    suspend fun deleteAllSuperheroes()
}