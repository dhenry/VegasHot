package com.vegashot.vegashot.scraper;

import com.vegashot.vegashot.model.ClassSchedule;

public interface HtmlScraper {

    ClassSchedule scrapeClassSchedule(final String html) throws ScraperException;
}
