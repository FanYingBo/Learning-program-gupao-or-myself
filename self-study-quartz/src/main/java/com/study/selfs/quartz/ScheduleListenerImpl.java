package com.study.selfs.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.*;

public class ScheduleListenerImpl implements SchedulerListener {

    private static final Log logger = LogFactory.getLog(ScheduleListenerImpl.class);

    @Override
    public void jobScheduled(Trigger trigger) {
        logger.info("trigger scheduled " + trigger.getKey().getName());
    }

    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        logger.info("trigger unscheduled " + triggerKey.getName());
    }

    @Override
    public void triggerFinalized(Trigger trigger) {
        logger.info("trigger finalized " + trigger.getKey().getName());
    }

    @Override
    public void triggerPaused(TriggerKey triggerKey) {
        logger.info("trigger Paused " + triggerKey.getName());
    }

    @Override
    public void triggersPaused(String triggerGroup) {
        logger.info("triggers Paused " + triggerGroup);
    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {
        logger.info("triggers Resumed " + triggerKey.getName());
    }

    @Override
    public void triggersResumed(String triggerGroup) {
        logger.info("triggers Resumed " + triggerGroup);
    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
        logger.info("job Added  " + jobDetail.getKey().getName());
    }

    @Override
    public void jobDeleted(JobKey jobKey) {
        logger.info("job deleted  " + jobKey.getName());
    }

    @Override
    public void jobPaused(JobKey jobKey) {
        logger.info("job paused  " + jobKey.getName());
    }

    @Override
    public void jobsPaused(String jobGroup) {
        logger.info("jobs paused  " + jobGroup);
    }

    @Override
    public void jobResumed(JobKey jobKey) {
        logger.info("job Resumed  " + jobKey.getName());
    }

    @Override
    public void jobsResumed(String jobGroup) {
        logger.info("jobs Resumed  " + jobGroup);
    }

    @Override
    public void schedulerError(String msg, SchedulerException cause) {
        cause.printStackTrace();
        logger.error(msg);
    }

    @Override
    public void schedulerInStandbyMode() {
        logger.info("scheduler InStandbyMode  ");
    }

    @Override
    public void schedulerStarted() {
        logger.info("scheduler Started  ");
    }

    @Override
    public void schedulerStarting() {
        logger.info("scheduler Starting  ");
    }

    @Override
    public void schedulerShutdown() {
        logger.info("scheduler Shutdown  ");
    }

    @Override
    public void schedulerShuttingdown() {
        logger.info("scheduler ShuttingDown  ");
    }

    @Override
    public void schedulingDataCleared() {
        logger.info("scheduler DataCleared  ");
    }
}
