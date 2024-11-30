package com.example.loginsignupfrd.ui.history

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class HistoryPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2 // Jumlah tab

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HistoryTabFragment.newInstance("History")
            1 -> FavoriteFragment.newInstance("Favorite")
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}