package com.example.onlinevideoplatform

import android.R.attr.path
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.onlinevideoplatform.databinding.ActivityIndividualVideoBinding
import com.example.onlinevideoplatform.model.ChannelModel
import com.example.onlinevideoplatform.model.LikeDislikeModel
import com.example.onlinevideoplatform.model.VideoDetailModel
import com.example.onlinevideoplatform.model.VideoInfo
import com.example.onlinevideoplatform.util.InitSupabase
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.launch


class IndividualVideoActivity : AppCompatActivity() {

    private lateinit var b:ActivityIndividualVideoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //view binding
        b = ActivityIndividualVideoBinding.inflate(layoutInflater)
        setContentView(b.root)

        //Getting intent values
        val vLink = intent.getStringExtra("videoLink")!!
        val vid = intent.getIntExtra("videoId", 0)
        val vName = intent.getStringExtra("videoTitle")!!
        val chName = intent.getStringExtra("channelName")!!
        val likes = intent.getIntExtra("likes",0)
        val dislikes = intent.getIntExtra("dislikes",0)

        //Setting values
        b.tvVideoTitle?.text = vName
        b.tvVideoLike?.text = "Likes : $likes"
        b.tvVideoDislike?.text = "Dislikes : $dislikes"
        b.tvChannelTitle?.text = "Channel : $chName"

        lifecycleScope.launch {
            //fetching video information
            fetchVideoInformation(vid)
        }

        //For video controls
        val mediaController = MediaController(this)
        mediaController.setAnchorView(b.vvIndView)

        b.vvIndView.setMediaController(mediaController)
        b.vvIndView.setVideoURI(Uri.parse(vLink))
        b.vvIndView.start()
    }

    private suspend fun fetchVideoInformation(vid:Int) {
        val videos = InitSupabase.supabase
            .from("VIDEO_DETAIL")
            .select(columns = Columns.list("CREATED_AT","DESCRIPTION")) {
                filter {
                    eq("ID", vid)
                }
            }
            .decodeList<VideoInfo>()


        b.tvVideoDescription?.text = "Description \n ${videos[0].DESCRIPTION}"

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Save the playback position
        outState.putInt("position", b.vvIndView.currentPosition)
        outState.putBoolean("isPlaying", b.vvIndView.isPlaying)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Restore the playback position
        b.vvIndView.seekTo(savedInstanceState.getInt("position"))
        if (savedInstanceState.getBoolean("isPlaying")) {
            b.vvIndView.start()
        }
    }
}