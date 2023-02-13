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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

@RestController
@RequestMapping(value = "/collection")
@RequiredArgsConstructor
@Slf4j
public class CollectionController {
    private final CollectionService collectionService;

    private final ChaeumValidation chaeumValidation;

    private static WebDriver driver;

    @Value("${chrome.driver-path}")
    private String CHROME_DRIVER_PATH;

    @Value("${chrome.driver-id}")
    private String CHROME_DRIVER_ID;

    @GetMapping("/scheduler")
    public void collectionTaskExecute() {
        Executors.newSingleThreadExecutor().submit(() -> {
            driver = new ChromeDriver(setChromeOptions());
            try {
                this.collectionService.collectionTaskExecute(null, 0, 1, 0, 0, 0, 10, driver);
            } catch (Exception e) {
                log.error("GET BEST SELLER INFO ERROR : >> " + e);
            } finally {
                if (!this.chaeumValidation.isNull(driver)) driver.quit();
            }
        });
    }

    @GetMapping("/bestseller")
    public ResponseEntity<Map<String, Object>> getBestSellerInfo(@RequestParam Map<String, Object> map) {
        Executors.newSingleThreadExecutor().submit(() -> {
            driver = new ChromeDriver(setChromeOptions());
            try {
                return ResponseEntity.ok().body(this.collectionService.getBestSellerInfo(map, 0, 1, 0, 0, 0, 10, driver));
            } catch (Exception e) {
                log.error("GET BEST SELLER INFO ERROR : >> " + e);
                return this.chaeumValidation.badRequest("베스트셀러 정보를 가져오는데 실패하였습니다.");
            } finally {
                if(!this.chaeumValidation.isNull(driver)) driver.quit();
            }
        });
        return ResponseEntity.ok().body(this.chaeumValidation.successResult(null));
    }

    private ChromeOptions setChromeOptions() {
        System.setProperty(CHROME_DRIVER_ID, CHROME_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.setCapability("ignoreProtectedModeSettings", true);
        options.addArguments("headless");
        options.addArguments("no-sandbox");
        options.addArguments("disable-dev-shm-usage");
        options.addArguments("lang=ko");
        return options;
    }
}
