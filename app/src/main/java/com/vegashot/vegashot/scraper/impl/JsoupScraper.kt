package com.vegashot.vegashot.scraper.impl


import com.vegashot.vegashot.model.ClassSchedule
import com.vegashot.vegashot.model.Clazz
import com.vegashot.vegashot.model.DailySchedule
import com.vegashot.vegashot.scraper.HtmlScraper
import com.vegashot.vegashot.scraper.ScraperException

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import java.util.ArrayList

public class JsoupScraper : HtmlScraper {

    throws(ScraperException::class)
    override fun scrapeClassSchedule(html: String): ClassSchedule {

        try {
            val scheduleDoc = Jsoup.parse(html)

            // get the date headers
            val dateHeaders = scheduleDoc.select(".master_date")
            val dailySchedules = ArrayList<DailySchedule>()

            for (element in dateHeaders) {
                dailySchedules.add(DailySchedule(element.select(".title_date").text(), ArrayList<Clazz>()))
            }

            // get the schedule for each day

            val scheduleWrapper = scheduleDoc.select(".mb_schedule")

            val scheduleTables = scheduleWrapper.select("table")

            var currentDay = 0
            for (table in scheduleTables) {

                val dateRows = table.select(".row_date")
                val classRows = table.select(".row_class")
                val staffRows = table.select(".row_staff")

                for (i in dateRows.indices) {
                    val clazz = Clazz(dateRows.get(i).text(), classRows.get(i).select("a").text(), staffRows.get(i).select("a").text())
                    dailySchedules.get(currentDay).classes.add(clazz)
                }
                currentDay++
            }


            return ClassSchedule(dailySchedules)

        } catch (parseException: Exception) {
            throw ScraperException(parseException)
        }

    }
}
