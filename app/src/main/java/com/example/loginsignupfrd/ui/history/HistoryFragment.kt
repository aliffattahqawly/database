package com.example.loginsignupfrd.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.loginsignupfrd.databinding.FragmentHistoryBinding
import com.example.loginsignupfrd.ui.shared.SharedViewModel
import com.google.android.material.tabs.TabLayoutMediator

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = HistoryPagerAdapter(requireActivity())
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "History"
                1 -> "Favorite"
                else -> throw IllegalArgumentException("Invalid position")
            }
        }.attach()

        // Observe history items
        sharedViewModel.historyItems.observe(viewLifecycleOwner) { items ->
            // Update your adapter with the new items
            // For example, if you have a RecyclerView in HistoryTabFragment, update its adapter here
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}