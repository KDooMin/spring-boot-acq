package com.acq.collection.acqcollectionbook.homepage.collection;

import com.acq.collection.acqcollectionbook.common.validation.ChaeumValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CollectionController {
    private final CollectionService collectionService;

    private final ChaeumValidation chaeumValidation;

    private WebDriver driver;

    @Value("${chrome.driver-path}")
    private String CHROME_DRIVER_PATH;

    @Value("${chrome.driver-id}")
    private String CHROME_DRIVER_ID;

    @GetMapping
    public ResponseEntity getBestSellerInfo() {
        try {
            System.setProperty(CHROME_DRIVER_ID, CHROME_DRIVER_PATH);

            ChromeOptions options = new ChromeOptions();

            driver = new ChromeDriver(options);

            return ResponseEntity.ok().body(this.collectionService.getBestSellerInfo(driver));
        } catch (Exception e) {
            log.error("GET BEST SELLER INFO ERROR : >> " + e);
            return this.chaeumValidation.badRequest("베스트셀러 정보를 가져오는데 실패하였습니다.");
        } finally {
            driver.close();
        }
    }
}
