package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Lugar(
    @PrimaryKey
    val id: Int,
    val numero: String,
    val nombre: String,
    val descripcion: String
)
