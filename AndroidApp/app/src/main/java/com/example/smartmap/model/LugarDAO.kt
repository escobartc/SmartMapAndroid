package com.example.smartmap.model

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

    @Query(value = "SELECT numero FROM Lugar WHERE nombre = :nombre")
    suspend fun getNumeroByNombre(nombre: String): String

    @Query(value = "SELECT descripcion FROM Lugar WHERE numero = :numero")
    suspend fun getDescripcionByNumero(numero: String): String

    @Query(value = "SELECT imagen FROM Lugar WHERE numero = :numero")
    suspend fun getImagenByNumero(numero: String): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lugares : List<Lugar>)

    @Query("DELETE FROM Lugar")
    suspend fun nukeTable()
}