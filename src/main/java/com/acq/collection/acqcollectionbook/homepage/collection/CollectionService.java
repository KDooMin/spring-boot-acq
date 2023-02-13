package com.acq.collection.acqcollectionbook.homepage.collection;

import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;

public interface CollectionService {
    void collectionTaskExecute(Map<String, Object> params, int _siteIndex, int _page, int _categoryIndex, int _listIndex, int _rank, int _tryIndex, WebDriver driver);

    Map<String, Object> getBestSellerInfo(Map<String, Object> params, int _siteIndex, int _page, int _categoryIndex, int _listIndex, int _rank, int _tryIndex, WebDriver driver);
}
