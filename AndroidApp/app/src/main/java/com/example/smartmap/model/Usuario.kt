package com.example.smartmap.model


data class Usuario(
    val uId: String,
    val email: String,
    val nombre: String,
    val nodoActual: Int ?= null,
    val historialDeBusqueda: String ?= null,
    val historialDeUbicaciones: HistorialDeUbicaciones ?= null
)