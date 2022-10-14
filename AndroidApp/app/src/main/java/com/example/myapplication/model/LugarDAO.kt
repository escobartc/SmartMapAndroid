package com.example.myapplication.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LugarDAO {
    @Query(value = "SELECT * FROM Lugar")
    suspend fun getAll(): List<Lugar>

    @Query(value = "SELECT nombre FROM Lugar WHERE numero = :numero")
    suspend fun getNombreByNumero(numero: String): String

    @Query(value = "SELECT descripcion FROM Lugar WHERE numero = :numero")
    suspend fun getDescripcionByNumero(numero: String): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lugares : List<Lugar>)

    @Query("DELETE FROM Lugar")
    suspend fun nukeTable()
}