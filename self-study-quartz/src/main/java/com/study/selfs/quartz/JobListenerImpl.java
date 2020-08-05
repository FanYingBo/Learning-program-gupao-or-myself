package com.study.selfs.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class JobListenerImpl implements JobListener {

    private static final Log log = LogFactory.getLog(JobListenerImpl.class);

    @Override
    public String getName() {
        return "test-job-listener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        log.info("job was ToBeExecuted "+context.getJobDetail().getKey().getName());
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        log.info("job was Vetoed "+context.getJobDetail().getKey().getName());
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        log.info("job was Executed "+context.getJobDetail().getKey().getName());
    }
}
