package com.acq.collection.acqcollectionbook.config.scheduler;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public abstract class DynamicAbstractScheduler {
    private ThreadPoolTaskScheduler scheduler;

    public void stopScheduler() {
        if(this.scheduler == null) return;
        this.scheduler.shutdown();
    }

    public void startScheduler() {
        this.scheduler = new ThreadPoolTaskScheduler();
        this.scheduler.initialize();
        this.scheduler.schedule(getRunnable(), getTrigger());
    }

    public void restartScheduler() {
        this.stopScheduler();
        this.startScheduler();
    }

    private Runnable getRunnable() {
        return this::runner;
    }

    public abstract void runner();

    public abstract Trigger getTrigger();
}
