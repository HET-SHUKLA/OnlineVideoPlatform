package com.example.onlinevideoplatform.model

import kotlinx.serialization.Serializable

@Serializable
data class VideoModel (
    val VIDEO_ID: Int,
    val CHANNEL_ID: Int,
    val VIDEO_NAME:String,
    val VIDEO_LINK:String
)