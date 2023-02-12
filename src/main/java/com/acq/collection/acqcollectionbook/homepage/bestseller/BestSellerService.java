package com.acq.collection.acqcollectionbook.homepage.bestseller;

import org.openqa.selenium.WebDriver;

import java.util.Map;

public interface BestSellerService {
    Map<String, Object> getBestSellerInfo(WebDriver driver);
}
