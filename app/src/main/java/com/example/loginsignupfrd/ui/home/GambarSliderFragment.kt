package com.example.loginsignupfrd.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.loginsignupfrd.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GambarSliderFragment : Fragment() {

    private lateinit var imageSlider: ImageSlider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_gambar_slider, container, false)
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

        return view
    }
}