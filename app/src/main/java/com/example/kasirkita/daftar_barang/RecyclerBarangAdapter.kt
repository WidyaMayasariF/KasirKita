package com.example.kasirkita.daftar_barang

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kasirkita.databinding.ItemBarangBinding
import com.example.kasirkita.model.Product

class RecyclerBarangAdapter : RecyclerView.Adapter<RecyclerBarangAdapter.ViewHolder>() {
    private val productList = ArrayList<Product>()
    private lateinit var onEdit: (barang: Product) -> Unit
    private lateinit var onDelete: (barang: Product) -> Unit

    fun setList(movies: ArrayList<Product>) {
        productList.clear()
        productList.addAll(movies)
        notifyDataSetChanged()
    }

    fun setEditListener(click: (barang: Product) -> Unit) {
        onEdit = click
    }

    fun setDeleteListener(click: (barang: Product) -> Unit) {
        onDelete = click
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBarangBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productList[position])
        holder.binding.btnEdit.setOnClickListener {
            onEdit(productList[position])
        }
        holder.binding.btnDelete.setOnClickListener {
            onDelete(productList[position])
        }
    }

    override fun getItemCount(): Int = productList.size

    class ViewHolder(val binding: ItemBarangBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                txtId.text = "ID \t\t\t\t: ${product.id}"
                txtName.text = "Name \t: ${product.name}"
                txtPrice.text = "Harga \t: Rp. ${product.price}"
            }
        }
    }
}