package com.abmo.route.downloader.facebook

import com.abmo.module.APIResponse
import com.abmo.utils.VideoUtils
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.net.URL

fun Route.downloadFacebookVideo() {

    // Endpoint to download a Facebook video
    get("/download/facebook/video") {
        val url = call.parameters["u"]
        if (!url.isNullOrBlank()) {
            if (!isUrlValid(url)) {
                call.respond(HttpStatusCode.BadRequest, APIResponse("invalid url", null))
            } else {
                call.respond(HttpStatusCode.OK, VideoUtils().getVideoUrlsAndInfo(url))
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, APIResponse("invalid parameter value"))
        }
    }

}

fun isUrlValid(urlString: String): Boolean {
    return try {
        URL(urlString)
        // If the URL object is created without any exception, it means the URL is valid
        true
    } catch (e: Exception) {
        // If an exception occurs, it means the URL is invalid
        false
    }
}
