package com.abmo.utils

import com.abmo.Constants
import com.abmo.module.APIResponse
import com.abmo.module.Video
import com.abmo.module.VideoUrls
import org.json.JSONException
import org.json.JSONObject

class VideoUtils {

    private val extractDataFromUrl = ExtractDataFromUrl()

    fun getVideoUrlsAndInfo(url: String): APIResponse {

        // Extract JSON data from the video url
        val jsonData = extractDataFromUrl.getJsonDataOfVideo(url)
        val data = jsonData[Constants.VIDEO_URLS_KEY]
        val details = jsonData[Constants.VIDEO_DETAILS_KEY]


        // Extract video URLs and details from the JSON data
        val jsonArray = JSONObject(data)
            .getJSONArray("require")
            .getJSONArray(5)
            .getJSONArray(3)
            .getJSONObject(1)
            .getJSONObject("__bbox")
            .getJSONObject("result")


        val scrapedData = jsonArray
            .getJSONObject("data")
            .getJSONObject("video")
            .getJSONObject("story")
            .getJSONArray("attachments")
            .getJSONObject(0)
            .getJSONObject("media")


        val jsonObject = JSONObject(details)
            .getJSONObject("__bbox")
            .getJSONObject("result")
            .getJSONObject("data")


        // Extract resolutions and audio URLs from the JSON data
        val resolutionsList = jsonArray.getJSONObject("extensions")
            ?.getJSONArray("all_video_dash_prefetch_representations")
            ?.getJSONObject(0)
            ?.getJSONArray("representations")


        val resList = mutableListOf<VideoUrls>()
        var audio: String? = null
        if (resolutionsList != null) {
            // Iterate through resolutions list
            for (i in 1 until resolutionsList.length()) {
                val videoUrl = resolutionsList.getJSONObject(i).get("base_url")
                val resolutions = resolutionsList.getJSONObject(i).get("height")
                if (resolutions as Int != 0) {
                    // add video URL with resolutions to the list
                    resList.add(VideoUrls(videoUrl.toString(), resolutions.toString(), true, "video"))
                } else {
                    // store audio URL separately
                    audio = videoUrl.toString()
                }

            }
        }

        // Extract video details from the JSON data
        val videoTitle = try {
            jsonObject.getJSONObject("creation_story")
                .getJSONObject("message")
                .get("text")
        } catch (e: JSONException) {
            null
        }


        val viewsCount = jsonObject.getJSONObject("feedback")
            .getJSONObject("video_view_count_renderer")
            .getJSONObject("feedback")
            .get("video_view_count")

        val videoThumbnail = scrapedData
            .getJSONObject("preferred_thumbnail")
            .getJSONObject("image")
            .get("uri")

        val likesCount = jsonObject.getJSONObject("feedback")
            .getJSONObject("reaction_count")
            .get("count")

        val videoUrlSD = scrapedData.get("playable_url").toString()
        val videoUrlHD = scrapedData.get("playable_url_quality_hd").toString()

//    val audio = scrappedAudio.get("url")
        val duration = scrapedData.get("playable_duration_in_ms")
        val creationDate = scrapedData.get("publish_time")

        val videoUrls = mutableListOf<VideoUrls>()

        videoUrls.add(VideoUrls(videoUrlSD, "SD", false, "video"))
        videoUrls.add(VideoUrls(videoUrlHD, "HD", false, "video"))
        videoUrls.addAll(resList)

        val video = mutableListOf<Video>()
        video.add(
            Video(
                videoTitle.toString(),
                viewsCount as Int,
                likesCount as Int,
                creationDate as Int,
                duration as Int,
                videoThumbnail.toString(),
                audio,
                videoUrls
            )
        )


        return APIResponse("ok", video)

    }

}