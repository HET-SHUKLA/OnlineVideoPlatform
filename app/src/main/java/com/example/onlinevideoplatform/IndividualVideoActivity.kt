package com.example.onlinevideoplatform

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.onlinevideoplatform.databinding.ActivityIndividualVideoBinding

class IndividualVideoActivity : AppCompatActivity() {

    private lateinit var b:ActivityIndividualVideoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = ActivityIndividualVideoBinding.inflate(layoutInflater)
        setContentView(b.root)

        val vid = intent.getStringExtra("videoId")!!
//        val ch = intent.getStringExtra("channelId")!!

        b.vvIndView.setVideoURI(Uri.parse(vid))
        b.vvIndView.start()

    }
}