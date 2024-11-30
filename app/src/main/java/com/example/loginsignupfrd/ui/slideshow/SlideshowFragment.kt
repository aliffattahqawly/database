package com.example.loginsignupfrd.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginsignupfrd.databinding.FragmentSlideshowBinding
import androidx.appcompat.widget.SearchView
import com.example.loginsignupfrd.R
import com.example.loginsignupfrd.model.Item
import com.example.loginsignupfrd.ui.home.CardAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var cardAdapter: CardAdapter
    private val items: MutableList<Item> = mutableListOf()
    private val filteredItems: MutableList<Item> = mutableListOf()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Inisialisasi SearchView
        val searchView: androidx.appcompat.widget.SearchView = binding.searchView
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterItems(newText)
                return true
            }
        })

        // Inisialisasi RecyclerView
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        cardAdapter = CardAdapter(filteredItems)
        recyclerView.adapter = cardAdapter

        // Inisialisasi Firebase
        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("data")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                items.clear() // Bersihkan list sebelum menambahkan data baru
                for (dataSnapshot in snapshot.children) {
                    val data = dataSnapshot.getValue(Item::class.java)
                    data?.let { items.add(it) }
                }
                filterItems(searchView.query.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                error("doesn't exist view")
            }
        })

        // Set onClickListener untuk setiap item kategori
        binding.categoryItem1.setOnClickListener {
            onCategoryClick(it)
        }

        binding.categoryItem2.setOnClickListener {
            onCategoryClick(it)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Metode untuk menangani klik pada item kategori
    private fun onCategoryClick(view: View) {
        when (view.id) {
            R.id.category_item1 -> {
                Toast.makeText(context, "Category 1 selected", Toast.LENGTH_SHORT).show()
                // Tambahkan logika untuk menampilkan data sesuai kategori 1
            }
            R.id.category_item2 -> {
                Toast.makeText(context, "Category 2 selected", Toast.LENGTH_SHORT).show()
                // Tambahkan logika untuk menampilkan data sesuai kategori 2
            }
            // Tambahkan logika untuk item kategori lainnya
        }
    }

    // Metode untuk memfilter item berdasarkan query
    private fun filterItems(query: String?) {
        filteredItems.clear()
        val searchText = query?.toLowerCase() ?: ""
        for (item in items) {
            if (item.name.toLowerCase().contains(searchText) || item.description.toLowerCase().contains(searchText)) {
                filteredItems.add(item)
            }
        }
        cardAdapter.notifyDataSetChanged()
    }
}