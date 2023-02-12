package com.acq.collection.acqcollectionbook.homepage.bestseller;

import com.acq.collection.acqcollectionbook.common.validation.ChaeumValidation;
import com.acq.collection.acqcollectionbook.homepage.collection.CollectionDivision;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class BestSellerServiceImpl extends EgovAbstractServiceImpl implements BestSellerService {

    private final ChaeumValidation chaeumValidation;

    @Override
    public Map<String, Object> getBestSellerInfo() {
        String [] apiNames = {"KYOBO_API"};

        for(String api : apiNames) {
            CollectionDivision division = CollectionDivision.of(api);
            log.info(division.getApiTitle() + ":" + division.getApiUrl());
        }
        return chaeumValidation.successResult(null);
    }
}
