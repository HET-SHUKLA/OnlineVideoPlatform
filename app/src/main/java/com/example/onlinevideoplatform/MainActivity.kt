package com.example.onlinevideoplatform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlinevideoplatform.adapter.VideoAdapter
import com.example.onlinevideoplatform.databinding.ActivityMainBinding
import com.example.onlinevideoplatform.model.VideoModel
import com.example.onlinevideoplatform.util.InitSupabase
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    //View Binding
    private lateinit var b:ActivityMainBinding

    //Array and adapter to store video info
    private lateinit var videos:ArrayList<VideoModel>
    private lateinit var videoAdapter:VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Init all the views and variables
        initialize()

        //Fetch Videos into videos array list
        lifecycleScope.launch {
            fetchVideos()
        }



    }

    private suspend fun fetchVideos() {
        videos.add(InitSupabase.supabase
            .from("VIDEO_MASTER")
            .select().decodeSingle<VideoModel>())
    }

    private fun initialize() {
        //init b
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        //init Array and adapter
        videos = ArrayList()
        videoAdapter = VideoAdapter(this, videos)

        //Init Recycler View and assign layout manager
        b.rvMainAct.layoutManager = LinearLayoutManager(this)

        //assign adapter to recycler view
        b.rvMainAct.adapter = videoAdapter

    }
}