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
import com.example.onlinevideoplatform.util.InitSupabase
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.launch


class IndividualVideoActivity : AppCompatActivity() {

    private lateinit var b:ActivityIndividualVideoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = ActivityIndividualVideoBinding.inflate(layoutInflater)
        setContentView(b.root)

        val vLink = intent.getStringExtra("videoLink")!!
        val ch = intent.getIntExtra("channelId",0)
        val vid = intent.getIntExtra("videoId", 0)
        val vName = intent.getStringExtra("videoTitle")!!

        b.tvVideoTitle?.text = vName

        lifecycleScope.launch {
            fetchVideoInformation(ch, vid)
        }

        val mediaController = MediaController(this)
        mediaController.setAnchorView(b.vvIndView)

        b.vvIndView.setMediaController(mediaController)
        b.vvIndView.setVideoURI(Uri.parse(vLink))
        b.vvIndView.start()
    }

    private suspend fun fetchVideoInformation(ch:Int, vid:Int) {
        val videos = InitSupabase.supabase
            .from("VIDEO_DETAIL")
            .select(columns = Columns.list("CREATED_AT","DESCRIPTION")) {
                filter {
                    eq("ID", vid)
                }
            }
            .decodeList<VideoDetailModel>()

        val likeDislike = InitSupabase.supabase
            .from("LIKE_DISLIKE")
            .select(columns = Columns.list("IS_LIKE")) {
                filter {
                    eq("ID", vid)
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
                    eq("ID", ch)
                }
            }
            .decodeList<ChannelModel>()

        b.tvVideoDescription?.text = "Description \n ${videos[0].DESCRIPTION}"
        b.tvVideoLike?.text = "Likes : ${likes.toString()}"
        b.tvVideoDislike?.text = "Dislikes : ${dislikes.toString()}"
        b.tvChannelTitle?.text = "Channel : ${channelName[0].CHANNEL_NAME}"

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