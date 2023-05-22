package com.abmo.plugins

import com.abmo.route.downloader.facebook.downloadFacebookVideo
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {
        downloadFacebookVideo()
    }
}
