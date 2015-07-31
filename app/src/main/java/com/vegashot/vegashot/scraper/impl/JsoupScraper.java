package com.vegashot.vegashot.scraper.impl;


import com.vegashot.vegashot.model.ClassSchedule;
import com.vegashot.vegashot.model.Clazz;
import com.vegashot.vegashot.model.DailySchedule;
import com.vegashot.vegashot.scraper.HtmlScraper;
import com.vegashot.vegashot.scraper.ScraperException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class JsoupScraper implements HtmlScraper {


    @Override
    public ClassSchedule scrapeClassSchedule(String html) throws ScraperException {

        try {
            Document scheduleDoc = Jsoup.parse(html);

            // get the date headers
            Elements dateHeaders = scheduleDoc.select(".master_date");
            List<DailySchedule> dailySchedules = new ArrayList<>();

            for (Element element : dateHeaders) {
                dailySchedules.add(new DailySchedule(element.select(".title_date").text(), new ArrayList<Clazz>()));
            }

            // get the schedule for each day

            Elements scheduleWrapper = scheduleDoc.select(".mb_schedule");

            Elements scheduleTables = scheduleWrapper.select("table");

            int currentDay = 0;
            for (Element table : scheduleTables) {

                Elements dateRows = table.select(".row_date");
                Elements classRows = table.select(".row_class");
                Elements staffRows = table.select(".row_staff");

                for (int i = 0; i < dateRows.size(); i++) {
                    Clazz clazz = new Clazz(dateRows.get(i).text(), classRows.get(i).select("a").text(), staffRows.get(i).select("a").text());
                    dailySchedules.get(currentDay).getClasses().add(clazz);
                }
                currentDay++;
            }


            return new ClassSchedule(dailySchedules);

        } catch (Exception parseException) {
            throw new ScraperException(parseException);
        }
    }
}
