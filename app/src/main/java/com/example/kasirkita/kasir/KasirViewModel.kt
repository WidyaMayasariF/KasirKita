package com.example.kasirkita.kasir

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kasirkita.database.BarangDao
import com.example.kasirkita.database.BarangDatabase
import com.example.kasirkita.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KasirViewModel(application: Application) : AndroidViewModel(application) {
    private var barangDao: BarangDao? = null
    private val _listBelanja = MutableLiveData<MutableList<Product>>()
    val listBelanja get() = _listBelanja

    init {
        barangDao = BarangDatabase.getInstance(application)?.barangDao()
        _listBelanja.value = ArrayList()
    }

    fun addBarangBelanja(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val barang = barangDao?.findBarangById(id)
            if (barang != null) {
                _listBelanja.value?.add(barang)
                _listBelanja.postValue(_listBelanja.value)
            }
        }
    }

    fun deleteBarangBelanja(barang: Product) {
        _listBelanja.value?.remove(barang)
        _listBelanja.value = _listBelanja.value
    }
}