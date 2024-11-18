package com.example.loginsignupfrd.ui.deskripsi

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.loginsignupfrd.R

class DetailItemActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var btnDownloadFile: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_item)

        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val videoUrl = intent.getStringExtra("videoUrl")
        val fileUrl = intent.getStringExtra("fileUrl")

        val tvDetailTitle: TextView = findViewById(R.id.tvDetailTitle)
        val tvDetailDescription: TextView = findViewById(R.id.tvDetailDescription)
        videoView = findViewById(R.id.videoView)
        btnDownloadFile = findViewById(R.id.btnDownloadFile)

        tvDetailTitle.text = title
        tvDetailDescription.text = description

        // Set video URL
        videoView.setVideoPath(videoUrl)
        videoView.start()

        // Set file download button
        btnDownloadFile.setOnClickListener {
            downloadFile(fileUrl)
        }
    }

    private fun downloadFile(fileUrl: String?) {
        if (fileUrl == null) return

        val request = DownloadManager.Request(Uri.parse(fileUrl))
            .setTitle("Downloading File")
            .setDescription("Downloading")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "file_name")

        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }
}