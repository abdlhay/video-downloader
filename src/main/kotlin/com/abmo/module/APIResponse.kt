package com.abmo.module

import kotlinx.serialization.Serializable

@Serializable
data class APIResponse(
    val status: String? = null,
    val result: List<Video>? = null,
)
