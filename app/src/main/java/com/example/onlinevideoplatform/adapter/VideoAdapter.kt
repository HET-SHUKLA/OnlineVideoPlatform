package com.example.onlinevideoplatform.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinevideoplatform.IndividualVideoActivity
import com.example.onlinevideoplatform.R
import com.example.onlinevideoplatform.model.VideoDetailModel
import com.example.onlinevideoplatform.model.VideoModel

class VideoAdapter(private val context:Context, private val videos:List<VideoDetailModel>):
    RecyclerView.Adapter<VideoAdapter.VideoHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoAdapter.VideoHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.video_recycler_view, parent, false)

        return VideoHolder(view)
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {

        val videoModel = videos[position].videoModel
        val channelModel = videos[position].channelModel
        val likeDislike = videos[position]


        holder.title.text = videoModel.VIDEO_NAME


        holder.info.text = "Channel : ${channelModel.CHANNEL_NAME}\n" +
                "Likes : ${likeDislike.likes} | Dislikes : ${likeDislike.disLike}"

        //Setting up the thumbnail
        setThumbnail(videoModel, holder)

        holder.ll.setOnClickListener {
            val i = Intent(context, IndividualVideoActivity::class.java)
            i.putExtra("videoLink", videoModel.VIDEO_LINK)
            i.putExtra("videoId", videoModel.VIDEO_ID)
            i.putExtra("videoTitle", videoModel.VIDEO_NAME)
            i.putExtra("channelName", channelModel.CHANNEL_NAME)
            i.putExtra("likes", likeDislike.likes)
            i.putExtra("dislikes", likeDislike.disLike)
            context.startActivity(i)
        }
    }

    private fun setThumbnail(current: VideoModel, holder: VideoHolder) {
        val videoUrl = current.VIDEO_LINK

        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(videoUrl, HashMap())

        // Get the frame at the 2nd second
        val bitmap: Bitmap = retriever.getFrameAtTime(2000000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)!!

        holder.thumbnail.setImageBitmap(bitmap)
    }

    override fun getItemCount(): Int = videos.size

    class VideoHolder(view: View) : RecyclerView.ViewHolder(view){
        val ll = view.findViewById<LinearLayout>(R.id.ll_video)
        val title = view.findViewById<TextView>(R.id.tv_tittle_rv)
        val info = view.findViewById<TextView>(R.id.tv_video_info)
        val thumbnail = view.findViewById<ImageView>(R.id.iv_video_rv)
    }
}