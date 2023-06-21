package com.yerayyas.marvelsuperheroes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SuperheroDao {
    @Query("SELECT * FROM superheroes_table")
    suspend fun getAllSuperheroes(): List<SuperheroEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSuperheroes(superheroes: List<SuperheroEntity>)

    @Query("DELETE FROM superheroes_table")
    suspend fun deleteAllSuperheroes()
}