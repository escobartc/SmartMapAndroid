package com.example.smartmap.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Edificio::class],
    version = 1
)
abstract class EdificiosDb : RoomDatabase() {
    abstract fun edificioDAO(): EdificioDAO
}