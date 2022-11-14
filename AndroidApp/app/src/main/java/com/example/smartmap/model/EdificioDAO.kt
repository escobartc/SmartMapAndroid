package com.example.smartmap.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EdificioDAO {
    @Query("SELECT * FROM Edificio")
    suspend fun getAllEdificios(): List<Edificio>

    @Query(value = "SELECT nombre FROM Edificio WHERE id_edificio = :numero")
    suspend fun getNombreByNumero(numero: Int): String

    @Query(value = "SELECT id_edificio FROM Edificio WHERE nombre = :nombre")
    suspend fun getNumeroByNombre(nombre: String): Int

    @Query(value = "SELECT descripcion FROM Edificio WHERE id_edificio = :numero")
    suspend fun getDescripcionByNumero(numero: Int): String

    @Query(value = "SELECT imagen FROM Edificio WHERE id_edificio = :numero")
    suspend fun getImagenByNumero(numero: Int): String

    @Query(value = "SELECT aforoActual FROM Edificio WHERE id_edificio = :numero")
    suspend fun getAforoByNumero(numero: Int): Int

    @Query(value = "SELECT palabras_clave FROM Edificio WHERE id_edificio = :numero")
    suspend fun getPalabrasClaveByNumero(numero: Int): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lugares: List<Edificio>)

    @Query("DELETE FROM Edificio")
    suspend fun nukeTable()
}