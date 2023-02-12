package com.acq.collection.acqcollectionbook.homepage.collection;

import com.acq.collection.acqcollectionbook.common.validation.ChaeumValidation;
import com.acq.collection.acqcollectionbook.homepage.bestseller.BestSellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CollectionController {

    private final CollectionService collectionService;

    private final ChaeumValidation chaeumValidation;

    @GetMapping
    public ResponseEntity getBestSellerInfo() {
        try {
            return ResponseEntity.ok().body(this.collectionService.getBestSellerInfo());
        } catch (Exception e) {
            log.error("GET BEST SELLER INFO ERROR : >> " + e);
            return this.chaeumValidation.badRequest("베스트셀러 정보를 가져오는데 실패하였습니다.");
        }
    }
}
