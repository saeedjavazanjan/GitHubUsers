package com.example.owlestictask.repository.local
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.owlestictask.model.User
import com.example.owlestictask.model.UserDetail


@Database(entities = [UserDetail::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dbDao(): DbDao
}