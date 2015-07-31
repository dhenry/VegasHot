package com.vegashot.vegashot.http

import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import com.vegashot.vegashot.model.ClassSchedule
import com.vegashot.vegashot.scraper.ScraperException
import com.vegashot.vegashot.scraper.impl.JsoupScraper

import java.io.IOException

public class VegasHotHttpClient {

    throws(IOException::class)
    private fun get(url: String): String {
        val request = Request.Builder().url(url).build()

        val response = client.newCall(request).execute()
        return response.body().string()
    }

    public fun getClassSchedule(): ClassSchedule? {
        try {
            return JsoupScraper().scrapeClassSchedule(get("http://www.vegashot.com/pages/rainbow"))
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } catch (e: ScraperException) {
            e.printStackTrace()
            return null
        }

    }

    companion object {

        private val client = OkHttpClient()
    }
}
