package com.example.kasirkita.tambah_barang

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.kasirkita.daftar_barang.TambahBarangViewModel
import com.example.kasirkita.databinding.ActivityTambahBarangBinding
import com.example.kasirkita.model.Product
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class TambahBarangActivity : AppCompatActivity() {
    lateinit var binding: ActivityTambahBarangBinding
    private val viewModel: TambahBarangViewModel by viewModels()

    var barang: Product? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahBarangBinding.inflate(layoutInflater)
        setContentView(binding.root)
        barang = intent.getParcelableExtra("barang")
        if (barang != null) {
            supportActionBar?.title = "Edit Barang"
            binding.btnDelete.visibility = View.VISIBLE
            binding.edtId.setText(barang!!.id)
            binding.edtNama.setText(barang!!.name)
            binding.edtHarga.setText(barang!!.price.toString())
            binding.btnTambah.text = "Edit Barang"
        } else {
            supportActionBar?.title = "Tambah Barang"
            binding.btnDelete.visibility = View.GONE
        }

        binding.btnDelete.setOnClickListener {
            if (barang != null) {
                viewModel.deleteBarang(barang!!)
                finish()
            }
        }

        binding.btnScan.setOnClickListener {
            val options = ScanOptions()
            options.setBeepEnabled(false)
            options.setOrientationLocked(false)
            barcodeLauncher.launch(options)
        }

        binding.btnTambah.setOnClickListener {
            if (binding.edtId.text.toString().isEmpty()) {
                binding.edtId.error = "ID tidak boleh kosong"
                return@setOnClickListener
            }
            if (binding.edtNama.text.toString().isEmpty()) {
                binding.edtNama.error = "Nama tidak boleh kosong"
                return@setOnClickListener
            }
            if (binding.edtHarga.text.toString().isEmpty()) {
                binding.edtHarga.error = "Harga tidak boleh kosong"
                return@setOnClickListener
            }
            val id = binding.edtId.text.toString()
            val name = binding.edtNama.text.toString()
            val harga: Int = Integer.parseInt(binding.edtHarga.text.toString())
            val newBarang = Product(id, name, harga)
            if (barang != null) {
                viewModel.updateBarang(newBarang)
            } else {
                viewModel.addBarang(newBarang)
            }
            finish()
        }

    }

    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(this@TambahBarangActivity, "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            binding.edtId.setText(result.contents.toString())
        }
    }
}
