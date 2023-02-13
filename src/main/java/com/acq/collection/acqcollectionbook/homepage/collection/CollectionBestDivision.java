package com.acq.collection.acqcollectionbook.homepage.collection;

import java.util.Arrays;

public enum CollectionBestDivision {
    KYOBO_API(
            "KYOBO_API",
            "https://product.kyobobook.co.kr",
            "/bestseller/total?period=002#?page=1&per=20&period=002&ymw=&bsslBksClstCode=",
            "button[class=tab_link] > span[class=tab_text]",
            "data-value",
            ""
    );

    private final String apiName;
    private final String apiUrl;
    private final String categoryUrl;
    private final String categoryEl;
    private final String categoryAttr;
    private final String categoryRegExp;

    CollectionBestDivision(String apiName, String apiUrl, String categoryUrl, String categoryEl, String categoryAttr, String categoryRegExp) {
        this.apiName = apiName;
        this.apiUrl = apiUrl;
        this.categoryUrl = categoryUrl;
        this.categoryEl = categoryEl;
        this.categoryAttr = categoryAttr;
        this.categoryRegExp = categoryRegExp;
    }

    public String getApiName() {
        return this.apiName;
    }

    public String getApiUrl() {
        return this.apiUrl;
    }

    public String getCategoryUrl() { return this.categoryUrl; }

    public String getCategoryEl() { return this.categoryEl; }

    public String getCategoryAttr() { return this.categoryAttr; }

    public String getCategoryRegExp() { return this.categoryRegExp; }

    public static CollectionBestDivision of(String apiName) {
        return Arrays.stream(CollectionBestDivision.values())
                .filter(d -> d.getApiName().equals(apiName))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("부적절한 API 입니다."));
    }
}
