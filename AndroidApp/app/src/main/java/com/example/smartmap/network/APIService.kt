package com.example.smartmap.network

import com.example.smartmap.model.EdificioDataCollection
import com.example.smartmap.model.Ruta
import com.example.smartmap.model.Usuario
import retrofit2.Response
import retrofit2.http.*

interface APIService {
    @GET("api/edificios/{uid}")
    suspend fun getEdificio(@Path("uid") uId: String): Response<EdificioDataCollection>

    @GET("api/ruta/{nodoInicio}/{nodoFin}")
    suspend fun getRutaMejorada(
        @Path("nodoInicio") nodoInicio: String,
        @Path("nodoFin") nodoFin: String
    ): Response<Ruta>

    @POST("api/usuarios")
    suspend fun registrarUsuario(@Body usuarios: Usuario): Response<*>

    @PUT("api/ActualizarPosicionUsuario/{uId}/{nodoAnterior}/{nodoActual}")
    suspend fun actualizarUbicacion(
        @Path("uId") uId: String,
        @Path("nodoAnterior") nodoAnterior: String, @Path("nodoActual") nodoActual: String
    ): Response<*>

    @PUT("api/GuardarBusquedaDelUsuario/{uId}/{busqueda}")
    suspend fun guardarBusquedaDelUsuario(
        @Path("uId") uId: String,
        @Path("busqueda") busqueda: String
    ): Response<*>

    @GET("api/aforo/{idEdificio}")
    suspend fun getAforo(@Path("idEdificio") idEdificio: String): Response<List<Int>>

}