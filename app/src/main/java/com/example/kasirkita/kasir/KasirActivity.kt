package com.example.kasirkita.kasir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kasirkita.MainActivity
import com.example.kasirkita.daftar_barang.RecyclerBarangAdapter
import com.example.kasirkita.databinding.ActivityKasirBinding
import com.example.kasirkita.model.Product
import com.example.kasirkita.tambah_barang.TambahBarangActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class KasirActivity : AppCompatActivity() {
    lateinit var binding: ActivityKasirBinding
    lateinit var recyclerBarangAdapter: RecyclerBarangAdapter
    private val viewModel: KasirViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKasirBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Total Belanja"

        recyclerBarangAdapter = RecyclerBarangAdapter()

        recyclerBarangAdapter.setEditListener { barang -> null  }

        recyclerBarangAdapter.setDeleteListener { barang ->
            viewModel.deleteBarangBelanja(barang)
        }

        viewModel.listBelanja.observe(this) {
            recyclerBarangAdapter.setList(ArrayList(it))
            setPurchaseAmount(it)

            if (it.isEmpty()) {
                binding.txtKasirEmpty.visibility = View.VISIBLE
                binding.rvKasir.visibility = View.INVISIBLE
                binding.txtKasirTotal.visibility = View.INVISIBLE
                binding.fabKasirSelesai.visibility = View.INVISIBLE
            } else {
                binding.txtKasirEmpty.visibility = View.INVISIBLE
                binding.rvKasir.visibility = View.VISIBLE
                binding.txtKasirTotal.visibility = View.VISIBLE
                binding.fabKasirSelesai.visibility = View.VISIBLE
            }
        }

        binding.rvKasir.apply {
            adapter = recyclerBarangAdapter
            layoutManager = LinearLayoutManager(applicationContext)
            setHasFixedSize(true)
        }

        binding.fabKasirTambah.setOnClickListener {
            val options = ScanOptions()
            options.setBeepEnabled(false)
            options.setOrientationLocked(false)
            barcodeLauncher.launch(options)
        }

        binding.fabKasirSelesai.setOnClickListener {
            finish()
        }
    }

    private fun setPurchaseAmount(belanjaan: List<Product>) {
        var totalBelanjaan = 0
        for (barang in belanjaan) {
            totalBelanjaan += barang.price
        }
        binding.txtKasirTotal.text = "Total Belanjaan \t\t\t\t:\t\t\t\t Rp. $totalBelanjaan"
    }

    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(this@KasirActivity, "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            viewModel.addBarangBelanja(result.contents.toString())
        }
    }
}