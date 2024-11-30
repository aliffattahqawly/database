package com.example.loginsignupfrd.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        cardAdapter = CardAdapter(items)
        recyclerView.adapter = cardAdapter


        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("data")


        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                items.clear() // Bersihkan list sebelum menambahkan data baru
                for (dataSnapshot in snapshot.children) {
                    val data = dataSnapshot.getValue(Item::class.java)
                    data?.let { items.add(it) }
                }
                cardAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                error("doesn't exist view")
            }
        })


        return view
    }
}