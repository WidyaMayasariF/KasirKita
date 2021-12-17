package com.example.kasirkita

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kasirkita.daftar_barang.DaftarBarangActivity
import com.example.kasirkita.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_KasirKita_NoActionBar)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.daftarBarang.setOnClickListener {
            Intent(applicationContext, DaftarBarangActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.kasir.setOnClickListener {
            // TODO : implement navigation ke halaman kasir
        }
    }
}