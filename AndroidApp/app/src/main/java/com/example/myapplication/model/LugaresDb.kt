package com.example.myapplication.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Lugar::class],
    version = 1
)
abstract class LugaresDb : RoomDatabase() {
    abstract fun lugarDao(): LugarDAO
}