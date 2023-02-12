package com.acq.collection.acqcollectionbook.config.scheduler.task;

import com.acq.collection.acqcollectionbook.config.scheduler.DynamicAbstractScheduler;
import com.acq.collection.acqcollectionbook.homepage.collection.CollectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CollectionJob extends DynamicAbstractScheduler {
    private final CollectionService collectionService;

    @Value("${spring.profiles.active}")
    private String activeType;

    @Override
    public void runner() {
        collectionService.collectionTask();
    }

    @Override
    public Trigger getTrigger() {
        String cron = "0/1 * * * * *";
        return new CronTrigger(cron);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void start() {
        if("stage".equals(activeType)) {
            super.restartScheduler();
        } else {
            log.info("local 모드에서는 장비 활성화 스케줄러 실행하지 않음");
            super.stopScheduler();
        }
    }
}
