package com.example.loginsignupfrd.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.loginsignupfrd.R
import com.example.loginsignupfrd.model.Item
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private lateinit var imageSlider: ImageSlider
    private lateinit var recyclerView: RecyclerView
    private lateinit var cardAdapter: CardAdapter
    private lateinit var items: MutableList<Item>
    private lateinit var allItems: MutableList<Item> // Untuk menyimpan semua data

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Image Slider
        imageSlider = view.findViewById(R.id.image_Slider)
        val imageList: MutableList<SlideModel> = ArrayList()

        FirebaseDatabase.getInstance().reference.child("imageSlider")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        imageList.add(
                            SlideModel(
                                data.child("url").value.toString(),
                                data.child("title").value.toString(),
                                ScaleTypes.FIT
                            )
                        )
                    }
                    imageSlider.setImageList(imageList, ScaleTypes.FIT)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle possible errors here
                }
            })

        // Card View
        recyclerView = view.findViewById(R.id.card_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context) // Pastikan LayoutManager sudah diatur
        items = mutableListOf()
        allItems = mutableListOf() // Inisialisasi allItems
        cardAdapter = CardAdapter(items)
        recyclerView.adapter = cardAdapter

        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("data")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                allItems.clear() // Bersihkan list sebelum menambahkan data baru
                for (dataSnapshot in snapshot.children) {
                    val data = dataSnapshot.getValue(Item::class.java)
                    data?.let { allItems.add(it) }
                }
                items.clear()
                items.addAll(allItems) // Tampilkan semua data saat pertama kali
                cardAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                error("doesn't exist view")
            }
        })

        // Tab Menu
        val tabMenu = view.findViewById<LinearLayout>(R.id.tabMenu)
        val materiBaruButton = tabMenu.getChildAt(0) as Button
        val materiPopulerButton = tabMenu.getChildAt(1) as Button
        val lainnyaButton = tabMenu.getChildAt(2) as Button

        materiBaruButton.setOnClickListener {
            filterItems("baru")
        }

        materiPopulerButton.setOnClickListener {
            filterItems("populer")
        }

        lainnyaButton.setOnClickListener {
            filterItems("Lainnya")
        }

        return view
    }

    private fun filterItems(category: String) {
        items.clear()
        if (category == "Lainnya") {
            items.addAll(allItems) // Tampilkan semua data jika kategori adalah "Lainnya"
        } else {
            for (item in allItems) {
                if (item.category == category) {
                    items.add(item)
                }
            }
        }
        cardAdapter.notifyDataSetChanged()
    }
}