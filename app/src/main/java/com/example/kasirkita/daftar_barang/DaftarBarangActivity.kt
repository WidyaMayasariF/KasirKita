package com.example.kasirkita.daftar_barang

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kasirkita.databinding.ActivityDaftarBarangBinding
import com.example.kasirkita.model.Product
import com.example.kasirkita.tambah_barang.TambahBarangActivity

class DaftarBarangActivity : AppCompatActivity() {
    lateinit var binding: ActivityDaftarBarangBinding
    lateinit var recyclerBarangAdapter: RecyclerBarangAdapter
    private val viewModel: DaftarBarangViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarBarangBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Daftar Barang"

        recyclerBarangAdapter = RecyclerBarangAdapter()
        recyclerBarangAdapter.setEditListener { barang ->
            Intent(applicationContext, TambahBarangActivity::class.java).also {
                it.putExtra("barang", barang)
                startActivity(it)
            }
        }
        recyclerBarangAdapter.setDeleteListener { barang ->
            viewModel.deleteBarang(barang)
        }

        viewModel.listBarang.observe(this) {
            recyclerBarangAdapter.setList(ArrayList(it))

            if (it.isEmpty()) {
                binding.txtEmpty.visibility = View.VISIBLE
                binding.rvBarang.visibility = View.INVISIBLE
            } else {
                binding.txtEmpty.visibility = View.INVISIBLE
                binding.rvBarang.visibility = View.VISIBLE
            }
        }

        binding.rvBarang.apply {
            adapter = recyclerBarangAdapter
            layoutManager = LinearLayoutManager(applicationContext)
            setHasFixedSize(true)
        }

        binding.fabTambah.setOnClickListener {
            Intent(applicationContext, TambahBarangActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}