package com.abmo.module

import kotlinx.serialization.Serializable

@Serializable
data class Video(
    val title: String? = null,
    val views: Int? = null,
    val likes: Int? = null,
    val publish_date: Int? = null,
    val duration_in_ms: Int? = null,
    val thumbnail: String? = null,
    val audio: String? = null,
    val video_urls: List<VideoUrls>? = null
)
