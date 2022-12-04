package com.abmo.plugins

import com.abmo.route.downloader.facebook.downloadFacebookVideo
import io.ktor.server.application.*

fun Application.configureRouting() {

    downloadFacebookVideo()
//    routing {
//        get("/") {
//            call.respondText("Hello World!")
//        }
//    }
}
