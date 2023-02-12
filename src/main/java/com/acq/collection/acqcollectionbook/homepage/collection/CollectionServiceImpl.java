package com.acq.collection.acqcollectionbook.homepage.collection;

import com.acq.collection.acqcollectionbook.homepage.bestseller.BestSellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class CollectionServiceImpl extends EgovAbstractServiceImpl implements CollectionService {

    private final BestSellerService bestSellerService;

    @Override
    public void collectionTask(WebDriver driver) {
        bestSellerService.getBestSellerInfo(driver);
    }

    @Override
    public Map<String, Object> getBestSellerInfo(WebDriver driver) {
        return null;
    }
}
