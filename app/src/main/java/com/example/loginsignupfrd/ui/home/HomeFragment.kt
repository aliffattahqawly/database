package com.example.loginsignupfrd.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginsignupfrd.R
import com.example.loginsignupfrd.databinding.FragmentHomeBinding
import com.example.loginsignupfrd.model.Item

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CardAdapter
//    private val classList = listOf(
//        Item("Item 1", "All"),
//        Item("Item 2", "Popular"),
//        Item("Item 3", "Nearby"),
//        Item("Item 4", "Popular"),
//        Item("Item 5", "Nearby")
//    ) // Contoh data

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Inisialisasi RecyclerView
        val recyclerView: RecyclerView = binding.cardRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Inisialisasi Adapter
//        adapter = CardAdapter(classList)
        recyclerView.adapter = adapter

        // Inisialisasi Filter
        val filterAll = binding.filterAll
        val filterPopular = binding.filterPopular
        val filterNearby = binding.filterNearby

        val categoryPills = listOf(filterAll, filterPopular, filterNearby)

        // Set default selected to "All"
        filterAll.isSelected = true

        categoryPills.forEach { pill ->
            pill.setOnClickListener {
                categoryPills.forEach { it.isSelected = false }
                pill.isSelected = true

                val category = when (pill.id) {
                    R.id.filter_all -> null
                    R.id.filter_popular -> "Popular"
                    R.id.filter_nearby -> "Nearby"
                    else -> null
                }

                val filteredList = if (category == null) {
//                    classList
                } else {
//                    classList.filter { it.category == category }
                }

//                adapter.updateList(filteredList)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}