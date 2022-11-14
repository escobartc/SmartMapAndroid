package com.example.smartmap.model


data class EdificioDataCollectionItem(
    val aforoActual: Int? = 0,
    val descripcion: String = "",
    val espacios: List<Espacio>? = emptyList(),
    val id_edificio: Int,
    val imagen: String? = "",
    val listaDeEntradas: String? = "",
    val nombre: String,
    val palabras_clave: String? = ""
)
