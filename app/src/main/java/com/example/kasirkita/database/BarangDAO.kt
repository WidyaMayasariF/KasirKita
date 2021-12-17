package com.example.kasirkita.database

import androidx.room.*
import com.example.kasirkita.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface BarangDao {

    @Query("SELECT * from barang")
    fun getListBarang(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBarang(barang: Product) : Unit

    @Delete
    suspend fun deleteBarang(barang: Product) : Unit

    @Update
    suspend fun editBarang(barang: Product) : Unit

}