package com.example.onlinevideoplatform.model

import kotlinx.serialization.Serializable

@Serializable
data class VideoDetailModel(

    val videoModel: VideoModel,
    val likes: Int,
    val disLike: Int,
    val channelModel: ChannelModel

)
