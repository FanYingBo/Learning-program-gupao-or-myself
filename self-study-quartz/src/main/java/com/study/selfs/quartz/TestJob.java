package com.study.selfs.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Calendar;

/**
 * 任务每次执行都是通过线程池来执行
 */
public class TestJob implements Job {

    private static final Log logger = LogFactory.getLog(TestJob.class);

    public TestJob(){

    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.error(Calendar.getInstance().getTime()+" job name： "+jobExecutionContext.getJobDetail().getKey().getName());
    }
}
