package com.example.kasirkita.daftar_barang

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kasirkita.database.BarangDao
import com.example.kasirkita.database.BarangDatabase
import com.example.kasirkita.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TambahBarangViewModel(application: Application) : AndroidViewModel(application) {
    private var barangDao: BarangDao? = null

    init {
        barangDao = BarangDatabase.getInstance(application)?.barangDao()
    }

    fun addBarang(barang: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            barangDao?.addBarang(barang)
        }
    }

    fun updateBarang(barang: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            barangDao?.editBarang(barang)
        }
    }

    fun deleteBarang(barang: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            barangDao?.deleteBarang(barang)
        }
    }
}