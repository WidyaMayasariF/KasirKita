package com.example.kasirkita.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kasirkita.model.Product

@Database(entities = [Product::class], version = 1)
abstract class BarangDatabase : RoomDatabase() {
    abstract fun barangDao(): BarangDao

    companion object {
        private var INSTANCE: BarangDatabase? = null

        fun getInstance(context: Context): BarangDatabase? {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        BarangDatabase::class.java,
                        "barang_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}