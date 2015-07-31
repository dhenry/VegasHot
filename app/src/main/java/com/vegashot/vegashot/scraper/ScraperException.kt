package com.vegashot.vegashot.scraper

public class ScraperException : Exception {

    public constructor(detailMessage: String) : super(detailMessage) {
    }

    public constructor(detailMessage: String, throwable: Throwable) : super(detailMessage, throwable) {
    }

    public constructor(throwable: Throwable) : super(throwable) {
    }

}
