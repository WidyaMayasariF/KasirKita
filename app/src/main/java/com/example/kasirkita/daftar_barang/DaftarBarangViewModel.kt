package com.example.kasirkita.daftar_barang

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kasirkita.database.BarangDao
import com.example.kasirkita.database.BarangDatabase
import com.example.kasirkita.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DaftarBarangViewModel(application: Application) : AndroidViewModel(application) {
    private var barangDao: BarangDao? = null

    init {
        barangDao = BarangDatabase.getInstance(application)?.barangDao()
        getBarangList()
    }

    private val _listBarang = MutableLiveData<List<Product>>()
    val listBarang get() = _listBarang

    private fun getBarangList() {
        viewModelScope.launch(Dispatchers.IO) {
            barangDao?.getListBarang()?.collect {
                _listBarang.postValue(it)
            }
        }
    }

    fun addBarang() {
        viewModelScope.launch(Dispatchers.IO) {
            barangDao?.addBarang(Product("1", "Q-tela", 6000))
        }
    }

    fun deleteBarang(barang: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            barangDao?.deleteBarang(barang)
        }
    }
}