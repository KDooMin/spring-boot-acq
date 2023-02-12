package com.acq.collection.acqcollectionbook.homepage.bestseller;

import com.acq.collection.acqcollectionbook.common.validation.ChaeumValidation;
import com.acq.collection.acqcollectionbook.homepage.collection.CollectionDivision;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class BestSellerServiceImpl extends EgovAbstractServiceImpl implements BestSellerService {

    private final ChaeumValidation chaeumValidation;

    private Document document;

    @Override
    public Map<String, Object> getBestSellerInfo(WebDriver driver) {
        String [] apiNames = {"KYOBO_API"};
        try {
            for (String api : apiNames) {
                CollectionDivision division = CollectionDivision.of(api);
                document = Jsoup.connect(division.getApiUrl()).get();
                log.info(document.toString());
            }
        } catch (Exception e) {
            log.error("BEST SELLER ERROR INFO : >> " + e);
        }
        return chaeumValidation.successResult(null);
    }
}
