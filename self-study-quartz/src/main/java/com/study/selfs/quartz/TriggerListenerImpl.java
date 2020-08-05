package com.study.selfs.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

/**
 * {@link org.quartz.listeners.TriggerListenerSupport}
 */
public class TriggerListenerImpl implements TriggerListener {

    private static final Log log = LogFactory.getLog(TriggerListenerImpl.class);
    @Override
    public String getName() {
        return "test-trigger-listener";
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        log.info("trigger was fired "+trigger.getKey().getName());
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        log.info("trigger was misfired "+trigger.getKey().getName());
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        log.info("trigger was complete "+trigger.getKey().getName());
    }
}
