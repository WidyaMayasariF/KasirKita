package com.example.kasirkita.database

import androidx.room.*
import com.example.kasirkita.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface BarangDao {

    @Query("SELECT * from barang")
    fun getListBarang(): Flow<List<Product>>

    @Query("SELECT * FROM barang WHERE id = :id")
    fun findBarangById(id: String): Product

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBarang(barang: Product) : Unit

    @Delete
    suspend fun deleteBarang(barang: Product) : Unit

    @Update
    suspend fun editBarang(barang: Product) : Unit

}