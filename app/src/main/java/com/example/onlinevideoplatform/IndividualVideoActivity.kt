package com.example.onlinevideoplatform

import android.media.MediaController2
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.example.onlinevideoplatform.databinding.ActivityIndividualVideoBinding

class IndividualVideoActivity : AppCompatActivity() {

    private lateinit var b:ActivityIndividualVideoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = ActivityIndividualVideoBinding.inflate(layoutInflater)
        setContentView(b.root)

        val vid = intent.getStringExtra("videoId")!!
        val ch = intent.getIntExtra("channelId",0)

        val mediaController = MediaController(this)
        mediaController.setAnchorView(b.vvIndView)

        b.vvIndView.setMediaController(mediaController)
        b.vvIndView.setVideoURI(Uri.parse(vid))
        b.vvIndView.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (b.vvIndView.isPlaying) outState.putInt("pos", b.vvIndView.currentPosition)
    }
}