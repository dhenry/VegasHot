package com.vegashot.vegashot.http;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.vegashot.vegashot.model.ClassSchedule;
import com.vegashot.vegashot.scraper.ScraperException;
import com.vegashot.vegashot.scraper.impl.JsoupScraper;

import java.io.IOException;

public class VegasHotHttpClient {

    private static OkHttpClient client = new OkHttpClient();

    private String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public ClassSchedule getClassSchedule() {
        String response = null;
        try {
            response = get("http://www.vegashot.com/pages/rainbow");
            return new JsoupScraper().scrapeClassSchedule(response);
        } catch (IOException | ScraperException e) {
            e.printStackTrace();
            return null;
        }
    }
}
