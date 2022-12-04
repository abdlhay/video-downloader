package com.abmo.route.downloader.facebook

import com.abmo.module.APIResponse
import com.abmo.module.Video
import com.abmo.module.VideoUrls
import com.abmo.utils.ExtractDataFromUrl
import com.abmo.utils.VIDEO_DETAILS_KEY
import com.abmo.utils.VIDEO_URLS_KEY
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.json.JSONException
import org.json.JSONObject

val extractDataFromUrl = ExtractDataFromUrl()

fun Application.downloadFacebookVideo() {

    routing {
        get("/facebook/video") {
            val givenUrl = call.parameters["u"]
            if (!givenUrl.isNullOrBlank()) {
                if (!givenUrl.contains("https")) {
                    call.respond(APIResponse("invalid url", null))
                } else {
                    try {
                        call.respond(getVideoUrls(givenUrl))
                    } catch (e: JSONException) {
                        call.respond(APIResponse("can't retrieve this video's data"))
                    }

                }

            } else {
                call.respond(APIResponse("invalid parameter value"))
            }

        }
    }

}

fun getVideoUrls(url: String): APIResponse {

    val jsonData = extractDataFromUrl.getJsonDataOfVideo(url)

    val data = jsonData[VIDEO_URLS_KEY]
    val details = jsonData[VIDEO_DETAILS_KEY]

    val jsonArray = JSONObject(data).getJSONArray("require")
        .getJSONArray(5).getJSONArray(3)
        .getJSONObject(1).getJSONObject("__bbox").getJSONObject("result")

    val scrapedData =  jsonArray.getJSONObject("data")
        .getJSONObject("video").getJSONObject("story")
        .getJSONArray("attachments").getJSONObject(0).getJSONObject("media")


//    val scrappedAudio = jsonArray.getJSONObject("extensions")
//        .getJSONArray("prefetch_dash_segments").getJSONObject(0)
//        .getJSONArray("audio").getJSONObject(0)


    // getting post details

    val jsonObject = JSONObject(details).getJSONObject("__bbox")
        .getJSONObject("result").getJSONObject("data")


    // getting all resolutions and audio
    val resolutionsList = jsonArray.getJSONObject("extensions")
        .getJSONArray("all_video_dash_prefetch_representations")
        .getJSONObject(0).getJSONArray("representations")


    val resList = mutableListOf<VideoUrls>()
    var audio: String? = null
    for (i in 1 until resolutionsList.length()) {
        val videoUrl = resolutionsList.getJSONObject(i).get("base_url")
        val resolutions = resolutionsList.getJSONObject(i).get("height")
        if (resolutions as Int != 0) {
            resList.add(VideoUrls(videoUrl.toString(), resolutions.toString(), true, "video"))
        } else {
            audio = videoUrl.toString()
//            resList.add(VideoUrls(videoUrl.toString(), null, false, "audio"))
        }

    }

    val videoTitle: Any? = try {
        jsonObject.getJSONObject("creation_story")
            .getJSONObject("message").get("text")
    } catch (e: JSONException) {
        null
    }





    val views = jsonObject.getJSONObject("feedback").getJSONObject("video_view_count_renderer")
        .getJSONObject("feedback").get("video_view_count")

    val videoThumbnail = scrapedData
        .getJSONObject("preferred_thumbnail")
        .getJSONObject("image").get("uri")

    val likes = jsonObject.getJSONObject("feedback").getJSONObject("reaction_count").get("count")
    val sd = scrapedData.get("playable_url")
    val hd = scrapedData.get("playable_url_quality_hd")

//    val audio = scrappedAudio.get("url")
    val duration = scrapedData.get("playable_duration_in_ms")
    val creationDate = scrapedData.get("publish_time")

    val videoUrls = mutableListOf<VideoUrls>()

    videoUrls.add(VideoUrls(sd.toString(), "SD", false, "video"))
    videoUrls.add(VideoUrls(hd.toString(), "HD", false, "video"))
    videoUrls.addAll(resList)

    val video = mutableListOf<Video>()
    video.add(Video(
        videoTitle.toString(),
        views as Int,
        likes as Int,
        creationDate as Int,
        duration as Int,
        videoThumbnail.toString(),
        audio,
        videoUrls))


    return APIResponse("ok", video)

}