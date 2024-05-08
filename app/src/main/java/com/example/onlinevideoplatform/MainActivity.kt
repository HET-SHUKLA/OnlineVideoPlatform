package com.example.onlinevideoplatform

import android.R.attr
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlinevideoplatform.adapter.VideoAdapter
import com.example.onlinevideoplatform.databinding.ActivityMainBinding
import com.example.onlinevideoplatform.model.ChannelModel
import com.example.onlinevideoplatform.model.LikeDislikeModel
import com.example.onlinevideoplatform.model.VideoDetailModel
import com.example.onlinevideoplatform.model.VideoModel
import com.example.onlinevideoplatform.util.InitSupabase
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    //View Binding
    private lateinit var b:ActivityMainBinding

    //Array and adapter to store video info
    private lateinit var videos:MutableList<VideoModel>
    private lateinit var videoAdapter:VideoAdapter
    private lateinit var videoDetail:MutableList<VideoDetailModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Init all the views and variables
        initialize()

        //Fetch Videos into videos array list
        lifecycleScope.launch {
            fetchVideos()
        }

        b.edtSearchMain.doOnTextChanged { text, start, before, count ->
            filterVideo(text.toString())
        }
    }

    private fun filterVideo(query: String) {
        val video = videoDetail.filter { video ->
            video.videoModel.VIDEO_NAME.contains(query, ignoreCase = true)
        }

        videoAdapter = VideoAdapter(this, video)
        b.rvMainAct.adapter = videoAdapter
    }

    private suspend fun fetchVideos() {
         videos = InitSupabase.supabase
             .from("VIDEO_MASTER")
             .select().decodeList<VideoModel>().toMutableList()

        fetchVideoDetail(videos)
    }

    private suspend fun fetchVideoDetail(videos: MutableList<VideoModel>) {
        for(i in videos)
        {
            val likeDislike = InitSupabase.supabase
                .from("LIKE_DISLIKE")
                .select(columns = Columns.list("IS_LIKE")) {
                    filter {
                        eq("ID", i.VIDEO_ID)
                    }
                }
                .decodeList<LikeDislikeModel>()

            val likes = likeDislike.count{
                it.IS_LIKE
            }

            val dislikes = likeDislike.count{
                !it.IS_LIKE
            }

            val channelName = InitSupabase.supabase
                .from("CHANNEL_MASTER")
                .select(columns = Columns.list("CHANNEL_NAME")) {
                    filter {
                        eq("ID", i.CHANNEL_ID)
                    }
                }
                .decodeList<ChannelModel>()

            videoDetail.add(VideoDetailModel(i, likes, dislikes, channelName[0]))

            videoAdapter = VideoAdapter(this, videoDetail)
            b.rvMainAct.layoutManager = LinearLayoutManager(this)
            b.rvMainAct.adapter = videoAdapter
        }
    }

    private fun initialize() {
        //init b
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        videos = ArrayList()
        videoDetail = ArrayList()
        //Init Recycler View and assign layout manager



    //assign adapter to recycler view

    }
}