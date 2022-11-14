package com.example.smartmap.model

data class Espacio(
    val nombre: String,
    val piso: String,
    val prestamoEquipos: Boolean,
    val sePuedeComer: Boolean,
    val tipoEspacio: String
)