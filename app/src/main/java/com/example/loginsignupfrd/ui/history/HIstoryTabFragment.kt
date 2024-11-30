package com.example.loginsignupfrd.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginsignupfrd.databinding.FragmentHistoryTabBinding
import com.example.loginsignupfrd.ui.shared.SharedViewModel

class HistoryTabFragment : Fragment() {

    private var _binding: FragmentHistoryTabBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var historyAdapter: HistoryAdapter

    companion object {
        private const val ARG_TITLE = "title"

        fun newInstance(title: String): HistoryTabFragment {
            val fragment = HistoryTabFragment()
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
        _binding = FragmentHistoryTabBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        historyAdapter = HistoryAdapter(sharedViewModel.historyItems.value ?: mutableListOf())
        recyclerView.adapter = historyAdapter

        // Observe history items
        sharedViewModel.historyItems.observe(viewLifecycleOwner) { items ->
            historyAdapter.updateItems(items)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}