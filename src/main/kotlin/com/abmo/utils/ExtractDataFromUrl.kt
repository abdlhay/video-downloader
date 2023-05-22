package com.abmo.utils


import com.abmo.Constants.VIDEO_DETAILS_KEY
import com.abmo.Constants.VIDEO_URLS_KEY
import org.jsoup.Jsoup
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class ExtractDataFromUrl {

    private val starting = """"define":"""
    private val ending = """});});});"""

    private fun getHtmlPage(url: String) : String {
        val data = BufferedReader(InputStreamReader(URL(url).openStream()))
        var input: String?
        val stringBuffer = StringBuffer()
        while (true) {
            if (data.readLine().also { input = it } == null) break
            stringBuffer.append(input)
        }
        data.close()
        return stringBuffer.toString()
    }

    private fun getHtml(url: String) : Map<String, String> {
        val info = mutableMapOf<String, String>()
        val document = Jsoup.parse(getHtmlPage(url))
        val elements = document.getElementsByTag("script")
        for (data in elements) {
            if (data.toString().contains("video_view_count")) {
                info[VIDEO_DETAILS_KEY] = data.data().toString()
            }

            if (data.toString().contains("video_home_www_matcha_entities")) {
                info[VIDEO_URLS_KEY] = data.data().toString()
            }
        }
        return info
    }

    fun getJsonDataOfVideo(url: String): Map<String, String> {
        val data = mutableMapOf<String, String>()
        val jsonData = listOf(getHtml(url)[VIDEO_URLS_KEY]!!, getHtml(url)[VIDEO_DETAILS_KEY]!!)
        data[VIDEO_URLS_KEY] = substringBetween(jsonData[0], starting, ending)
        data[VIDEO_DETAILS_KEY] = substringBetween(jsonData[1], """"__bbox"""", "}]],")
        return data
    }


    private fun substringBetween(string: String, starting: String, ending: String): String {
        return if (arrayOf<Any>(string, starting, ending).isEmpty()) {
            "null"
        } else {
            val start = string.indexOf(starting)
            if (start != -1) {
                val end = string.indexOf(ending, start + starting.length)
                if (end != -1) {
                    return "{$starting${string.substring(start + starting.length, end)}}"
                }
            }
            "null"
        }
    }

}