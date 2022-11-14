package com.example.smartmap.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Edificio(
    val aforoActual: Int,
    val descripcion: String,
    @PrimaryKey
    val id_edificio: Int,
    val imagen: String,
    val listaDeEntradas: String,
    val nombre: String,
    val palabras_clave: String
)
