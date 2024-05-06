package com.example.onlinevideoplatform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlinevideoplatform.adapter.VideoAdapter
import com.example.onlinevideoplatform.databinding.ActivityMainBinding
import com.example.onlinevideoplatform.model.VideoModel
import com.example.onlinevideoplatform.util.InitSupabase
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    //View Binding
    private lateinit var b:ActivityMainBinding

    //Array and adapter to store video info
    private lateinit var videos:List<VideoModel>
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
        val videos = InitSupabase.supabase
            .from("VIDEO_MASTER")
            .select().decodeList<VideoModel>()

        Log.d("ERROR_POSTGRES", videos[0].VIDEO_NAME)
        videoAdapter = VideoAdapter(this, videos)
        b.rvMainAct.layoutManager = LinearLayoutManager(this)
        b.rvMainAct.adapter = videoAdapter
        videoAdapter.notifyItemInserted(videos.size)
    }

    private fun initialize() {
        //init b
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        //Init Recycler View and assign layout manager

        //assign adapter to recycler view

    }
}