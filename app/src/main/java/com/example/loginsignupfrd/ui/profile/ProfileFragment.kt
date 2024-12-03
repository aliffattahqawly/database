package com.example.loginsignupfrd.ui.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.loginsignupfrd.databinding.FragmentProfileBinding
import com.example.loginsignupfrd.model.UserData
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.*

class ProfileFragment : Fragment(), EditProfileDialogFragment.EditProfileListener {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference
    private lateinit var userIdToDisplay: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Inisialisasi Firebase Database
        database = FirebaseDatabase.getInstance().reference

        // Ambil ID pengguna dari SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        userIdToDisplay = sharedPreferences.getString("user_id", "") ?: ""

        // Set up TabLayout dan ViewPager2
        val tabLayout: TabLayout = binding.tabLayout
        val viewPager: ViewPager2 = binding.viewPager

        viewPager.adapter = ProfilePagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = if (position == 0) "Materi" else "Suka"
        }.attach()

        // Set teks deskripsi profil
        val profileDescription: TextView = binding.textDeskripsi
        profileViewModel.profileDescription.observe(viewLifecycleOwner) {
            profileDescription.text = it
        }

        // Muat gambar profil dan gambar latar belakang dari Firebase Realtime Database
        loadUserData()

        // Tangani klik tombol Edit Profile
        val btnEditProfile: Button = binding.btnEditProfile
        btnEditProfile.setOnClickListener {
            showEditProfileDialog()
        }

        return root
    }

    private fun loadUserData() {
        if (userIdToDisplay.isNotEmpty()) {
            database.child("users").child(userIdToDisplay).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val profileImageUrl = snapshot.child("profileImageUrl").getValue(String::class.java)
                    profileImageUrl?.let {
                        Log.d("ProfileFragment", "Profile Image URL: $it")
                        Glide.with(this@ProfileFragment)
                            .load(it)
                            .into(binding.profileImage)
                    }

                    val backgroundImageUrl = snapshot.child("backgroundImageUrl").getValue(String::class.java)
                    backgroundImageUrl?.let {
                        Log.d("ProfileFragment", "Background Image URL: $it")
                        Glide.with(this@ProfileFragment)
                            .load(it)
                            .into(binding.backgroundImage)
                    }

                    val name = snapshot.child("name").getValue(String::class.java)
                    name?.let {
                        Log.d("ProfileFragment", "Name: $it")
                        binding.profileText.text = it
                    }

                    val status = snapshot.child("status").getValue(String::class.java)
                    val profession = snapshot.child("profession").getValue(String::class.java)
                    val phoneNumber = snapshot.child("phoneNumber").getValue(String::class.java)
                    val origin = snapshot.child("origin").getValue(String::class.java)

                    val description = """
                        Status: $status
                        Profesi: $profession
                        Nomor Telepon: $phoneNumber
                        Asal: $origin
                    """.trimIndent()

                    Log.d("ProfileFragment", "Description: $description")
                    binding.textDeskripsi.text = description
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("ProfileFragment", "Database error: ${error.message}")
                }
            })
        } else {
            Log.e("ProfileFragment", "User ID is empty")
        }
    }

    private fun showEditProfileDialog() {
        val editProfileDialog = EditProfileDialogFragment()
        editProfileDialog.setEditProfileListener(this)
        editProfileDialog.show(childFragmentManager, "EditProfileDialog")
    }

    override fun onProfileEdited(
        profileImageUrl: String,
        backgroundImageUrl: String,
        name: String,
        status: String,
        profession: String,
        phoneNumber: String,
        origin: String
    ) {
        // Update gambar profil dan gambar latar belakang
        Glide.with(this)
            .load(profileImageUrl)
            .into(binding.profileImage)

        Glide.with(this)
            .load(backgroundImageUrl)
            .into(binding.backgroundImage)

        // Update nama profil
        binding.profileText.text = name

        // Update deskripsi profil
        val description = """
            Status: $status
            Profesi: $profession
            Nomor Telepon: $phoneNumber
            Asal: $origin
        """.trimIndent()

        binding.textDeskripsi.text = description

        // Update Firebase Realtime Database
        val profileUpdates = mapOf(
            "profileImageUrl" to profileImageUrl,
            "backgroundImageUrl" to backgroundImageUrl,
            "name" to name,
            "status" to status,
            "profession" to profession,
            "phoneNumber" to phoneNumber,
            "origin" to origin
        )
        database.child("users").child(userIdToDisplay).updateChildren(profileUpdates)
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