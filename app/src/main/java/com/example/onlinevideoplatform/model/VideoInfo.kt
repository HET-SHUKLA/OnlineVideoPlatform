package com.example.onlinevideoplatform.model

import kotlinx.serialization.Serializable

@Serializable
data class VideoInfo(
    val CREATED_AT:String,
    val DESCRIPTION:String
)
