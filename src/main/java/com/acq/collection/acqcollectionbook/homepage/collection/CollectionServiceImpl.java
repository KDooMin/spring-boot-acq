package com.acq.collection.acqcollectionbook.homepage.collection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CollectionServiceImpl extends EgovAbstractServiceImpl implements CollectionService {
    @Override
    public void collectionTask() {
        log.info("collection execute");
    }
}
