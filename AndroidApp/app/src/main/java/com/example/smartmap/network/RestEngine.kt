package com.example.smartmap.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestEngine {
    companion object {
        private const val URL = "https://smartmap-javeriana.herokuapp.com/"

        private fun getInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().serializeNulls().create()
                    )
                )
                .build()
        }

        val apiService = getInstance().create(APIService::class.java)
    }
}