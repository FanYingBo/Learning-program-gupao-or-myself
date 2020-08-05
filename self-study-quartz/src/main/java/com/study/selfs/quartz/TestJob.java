package com.study.selfs.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Calendar;

public class TestJob implements Job {

    public TestJob(){

    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(Calendar.getInstance().getTime()+" job name： "+jobExecutionContext.getJobDetail().getKey().getName());
    }
}
