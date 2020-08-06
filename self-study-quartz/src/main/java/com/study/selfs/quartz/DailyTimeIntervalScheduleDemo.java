package com.study.selfs.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 定时任务设置时间间隔，起始时间，结束时间
 */
public class DailyTimeIntervalScheduleDemo {

    public static void main(String[] args) {
        try {
            Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
            // 定时任务计划，可设置时间间隔
            DailyTimeIntervalScheduleBuilder dailyTimeIntervalSchedule = DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule();
            dailyTimeIntervalSchedule.withInterval(5, DateBuilder.IntervalUnit.SECOND);
            // 设置任务计划启动的时间
            dailyTimeIntervalSchedule.startingDailyAt(TimeOfDay.hourAndMinuteOfDay(19,20));
            // 定时任务结束时间
            dailyTimeIntervalSchedule.endingDailyAt(TimeOfDay.hourAndMinuteOfDay(19,25));
            TriggerKey triggerKey = new TriggerKey("test-trigger");
            DailyTimeIntervalTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    //.startAt(); 设置起始时间
                    .withSchedule(dailyTimeIntervalSchedule).build();
            JobKey jobKey = new JobKey("test-job");
            // 任务
            JobDetail job = JobBuilder.newJob()
                    .ofType(TestJob.class)
                    .withDescription("")
                    .withIdentity(jobKey)
                    .build();
            defaultScheduler.scheduleJob(job,trigger);

            defaultScheduler.start();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
