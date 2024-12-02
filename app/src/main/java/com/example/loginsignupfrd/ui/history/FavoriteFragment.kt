package com.example.loginsignupfrd.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginsignupfrd.databinding.FragmentFavoriteBinding
import com.example.loginsignupfrd.ui.shared.SharedViewModel

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter

    companion object {
        private const val ARG_TITLE = "title"

        fun newInstance(title: String): FavoriteFragment {
            val fragment = FavoriteFragment()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        favoriteAdapter = FavoriteAdapter(sharedViewModel.favoriteItems.value ?: mutableListOf())
        recyclerView.adapter = favoriteAdapter

        // Observe favorite items
        sharedViewModel.favoriteItems.observe(viewLifecycleOwner) { items ->
            favoriteAdapter.updateItems(items)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}