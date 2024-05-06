package com.example.onlinevideoplatform.adapter

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinevideoplatform.R
import com.example.onlinevideoplatform.model.VideoModel

class VideoAdapter(private val context:Context, private val videos:ArrayList<VideoModel>):
    RecyclerView.Adapter<VideoAdapter.VideoHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoAdapter.VideoHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.video_recycler_view, parent, false)

        return VideoHolder(view)
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {

        val current = videos[position]

        holder.title.text = current.VIDEO_NAME

        //Setting up the thumbnail
        setThumbnail(current, holder)
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