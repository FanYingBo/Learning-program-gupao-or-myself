package com.study.selfs.quartz;

import org.apache.log4j.PropertyConfigurator;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Calendar;

/**
 * quartz 克隆表达式实现
 */
public class QuartzCornBaseDemo {

    public static void main(String[] args) {

        PropertyConfigurator.configure(ClassLoader.getSystemResourceAsStream("log4j.properties"));
        try {
            // 1
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//            // 2
//            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
//            Scheduler scheduler = schedulerFactory.getScheduler();

            Calendar calendar = Calendar.getInstance();

            calendar.add(Calendar.MINUTE,2); // 两分钟后结束
            // 克隆表达式
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("*/5 * * * * ?");
            //触发器
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(new TriggerKey("test-trigger"))
                    .startNow()
                    .withSchedule(cronScheduleBuilder)
                    .endAt(calendar.getTime())
                    .build();
            // 任务
            JobDetail job = JobBuilder.newJob()
                    .ofType(TestJob.class)
                    .withDescription("")
                    .withIdentity(new JobKey("test-job"))
                    .build();

            scheduler.scheduleJob(job,trigger);

            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}

