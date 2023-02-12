package com.acq.collection.acqcollectionbook.homepage.collection;

import java.util.Arrays;

public enum CollectionDivision {
    KYOBO_API("KYOBO_API", "https://naver.com");

    private String apiTitle;
    private String apiUrl;

    CollectionDivision(String apiTitle, String apiUrl) {
        this.apiTitle = apiTitle;
        this.apiUrl = apiUrl;
    }

    public String getApiTitle() {
        return this.apiTitle;
    }

    public String getApiUrl() {
        return this.apiUrl;
    }

    public static CollectionDivision of(String title) {
        return Arrays.asList(CollectionDivision.values()).stream()
                .filter(d -> d.getApiTitle().equals(title))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("부적절한 API 입니다."));
    }
}
