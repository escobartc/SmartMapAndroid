package com.example.smartmap.model

import android.content.Context
import androidx.room.Room

class LugarApp() {
    val room by lazy {
        Room
            .databaseBuilder(applicationContext, LugaresDb::class.java, "lugar")
            .build()
    }
    companion object {
        lateinit var applicationContext: Context
    }
}