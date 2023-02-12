package com.acq.collection.acqcollectionbook.homepage.collection;

import org.openqa.selenium.WebDriver;

import java.util.Map;

public interface CollectionService {
    void collectionTask(WebDriver driver);

    Map<String, Object> getBestSellerInfo(WebDriver driver);
}
