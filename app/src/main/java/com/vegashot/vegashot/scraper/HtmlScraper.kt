package com.vegashot.vegashot.scraper

import com.vegashot.vegashot.model.ClassSchedule

public interface HtmlScraper {

    throws(ScraperException::class)
    public fun scrapeClassSchedule(html: String): ClassSchedule
}
