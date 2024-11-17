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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GambarSliderFragment : Fragment() {

    private lateinit var imageSlider: ImageSlider
    private lateinit var recyclerView: RecyclerView
    private lateinit var cardAdapter: CardAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_gambar_slider, container, false)
        imageSlider = view.findViewById(R.id.image_Slider)

        recyclerView = view.findViewById(R.id.card_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Contoh data
        val items = listOf(
            Item("Item 1", ""),
            Item("Item 2",""),
            Item("Item 3","")
        )

        cardAdapter = CardAdapter(items)
        recyclerView.adapter = cardAdapter

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



        return view
    }
}