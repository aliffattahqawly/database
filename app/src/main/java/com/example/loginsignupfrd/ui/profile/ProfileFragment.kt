package com.example.loginsignupfrd.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.loginsignupfrd.databinding.FragmentProfileBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.*

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance().reference

        // Set up TabLayout and ViewPager2
        val tabLayout: TabLayout = binding.tabLayout
        val viewPager: ViewPager2 = binding.viewPager

        viewPager.adapter = ProfilePagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = if (position == 0) "Materi" else "Likes"
        }.attach()

        // Set profile description text
        val profileDescription: TextView = binding.textDeskripsi
        profileViewModel.profileDescription.observe(viewLifecycleOwner) {
            profileDescription.text = it
        }

        // Load profile image and background image from Firebase Realtime Database
        database.child("profile").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val profileImageUrl = snapshot.child("profileImageUrl").getValue(String::class.java)
                profileImageUrl?.let {
                    Glide.with(this@ProfileFragment)
                        .load(it)
                        .into(binding.profileImage)
                }

                val backgroundImageUrl = snapshot.child("backgroundImageUrl").getValue(String::class.java)
                backgroundImageUrl?.let {
                    Glide.with(this@ProfileFragment)
                        .load(it)
                        .into(binding.backgroundImage)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class ProfilePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return if (position == 0) MateriFragment() else LikesFragment()
        }
    }
}