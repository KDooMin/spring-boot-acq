package com.acq.collection.acqcollectionbook.homepage.collection;

import com.acq.collection.acqcollectionbook.homepage.bestseller.BestSellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class CollectionServiceImpl extends EgovAbstractServiceImpl implements CollectionService {

    private final BestSellerService bestSellerService;

    @Override
    public void collectionTaskExecute(Map<String, Object> params, int _siteIndex, int _page, int _categoryIndex, int _listIndex, int _rank, int _tryIndex, WebDriver driver) {
        // 베스트 셀러 수집기
        bestSellerService.getBestSellerInfo(params, _siteIndex, _page, _categoryIndex, _listIndex, _rank, _tryIndex, driver);
    }

    @Override
    public Map<String, Object> getBestSellerInfo(Map<String, Object> params, int _siteIndex, int _page, int _categoryIndex, int _listIndex, int _rank, int _tryIndex, WebDriver driver) {
        return bestSellerService.getBestSellerInfo(params, _siteIndex, _page, _categoryIndex, _listIndex, _rank, _tryIndex, driver);
    }
}
