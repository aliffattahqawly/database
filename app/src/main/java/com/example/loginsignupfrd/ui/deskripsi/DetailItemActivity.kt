package com.example.loginsignupfrd.ui.deskripsi

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import com.example.loginsignupfrd.R
import androidx.media3.exoplayer.*
import androidx.media3.ui.PlayerView


class DetailItemActivity : AppCompatActivity() {

    private lateinit var playerView: PlayerView
    private lateinit var btnDownloadFile: Button
    private lateinit var ivPlayVideo: ImageView
    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_item)

        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val videoUrl = intent.getStringExtra("videoUrl")
        val fileUrl = intent.getStringExtra("fileUrl")

        val tvDetailTitle: TextView = findViewById(R.id.tvDetailTitle)
        val tvDetailDescription: TextView = findViewById(R.id.tvDetailDescription)
        playerView = findViewById(R.id.playerView)
        btnDownloadFile = findViewById(R.id.btnDownloadFile)
        ivPlayVideo = findViewById(R.id.ivPlayVideo)

        tvDetailTitle.text = title
        tvDetailDescription.text = description

        // Set file download button
        btnDownloadFile.setOnClickListener {
            downloadFile(fileUrl)
        }

        // Set play video button
        ivPlayVideo.setOnClickListener {
            initializePlayer(videoUrl)
            ivPlayVideo.visibility = ImageView.GONE // Sembunyikan ImageView setelah video dimulai
        }
    }

    private fun initializePlayer(videoUrl: String?) {
        player = ExoPlayer.Builder(this).build()
        playerView.player = player

        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.play()
    }

    private fun releasePlayer() {
        player?.release()
        player = null
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
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