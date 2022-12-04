package com.abmo.module

import kotlinx.serialization.Serializable

@Serializable
data class VideoUrls(
    val url: String? = null,
    val resolution: String? = null,
    val isMute: Boolean? = null,
    val type: String? = null
)
