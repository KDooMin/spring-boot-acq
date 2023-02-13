package com.acq.collection.acqcollectionbook.homepage.bestseller;

import org.openqa.selenium.WebDriver;

import java.util.Map;

public interface BestSellerService {
    Map<String, Object> getBestSellerInfo(Map<String, Object> params, int _siteIndex, int _page, int _categoryIndex, int _listIndex, int _rank, int _tryIndex, WebDriver driver);
}
