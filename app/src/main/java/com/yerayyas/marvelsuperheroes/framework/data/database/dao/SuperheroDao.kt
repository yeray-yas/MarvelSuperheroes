package com.yerayyas.marvelsuperheroes.framework.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yerayyas.marvelsuperheroes.framework.data.database.entities.SuperheroEntity

@Dao
interface SuperheroDao {

    @Query("SELECT * FROM superhero_table ORDER BY name DESC")
    suspend fun getAllSuperheroes():List<SuperheroEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(superheroes:List<SuperheroEntity>)

@Query("DELETE FROM superhero_table")
suspend fun deleteSuperheroes()
}