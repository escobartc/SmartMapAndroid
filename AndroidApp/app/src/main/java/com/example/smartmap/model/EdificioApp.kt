package com.example.smartmap.model

import android.content.Context
import androidx.room.Room

class EdificioApp() {
    val room by lazy {
        Room
            .databaseBuilder(applicationContext, EdificiosDb::class.java, "Edificio")
            .build()
    }

    companion object {
        lateinit var applicationContext: Context
    }
}