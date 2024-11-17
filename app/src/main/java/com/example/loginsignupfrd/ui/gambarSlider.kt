package com.example.loginsignupfrd

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class gambarSlider : AppCompatActivity() {

    private lateinit var imageSlider: ImageSlider

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gambar_slider)
        imageSlider = findViewById(R.id.image_Slider)

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

    }
}