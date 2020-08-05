package com.study.selfs.quartz;

import org.apache.log4j.PropertyConfigurator;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Calendar;

/**
 * quartz 克隆表达式实现
 *
 * {@link ScheduleBuilder}
 * 1.{@link SimpleScheduleBuilder}
 * 2.{@link DailyTimeIntervalScheduleBuilder}
 * 3.{@link CalendarIntervalScheduleBuilder}
 * 4.{@link CronScheduleBuilder}
 *
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
            TriggerKey triggerKey = new TriggerKey("test-trigger");
            //触发器
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .withSchedule(cronScheduleBuilder)
                    .endAt(calendar.getTime())
                    .build();
            JobKey jobKey = new JobKey("test-job");
            // 任务
            JobDetail job = JobBuilder.newJob()
                    .ofType(TestJob.class)
                    .withDescription("")
                    .withIdentity(jobKey)
                    .build();
            scheduler.getListenerManager().addTriggerListener(new TriggerListenerImpl());
            scheduler.getListenerManager().addSchedulerListener(new ScheduleListenerImpl());
            scheduler.scheduleJob(job,trigger);
            scheduler.start();
//            TimeUnit.SECONDS.sleep(15);
//            scheduler.interrupt(jobKey);
            // 停止执行但不是不执行，等唤醒后累计的任务次数会重新执行
//            scheduler.pauseJob(jobKey);
//            TimeUnit.SECONDS.sleep(15);
            // 唤醒的时候会把未执行重新执行
//            scheduler.resumeJob(jobKey);
//            scheduler.unscheduleJob(triggerKey);
//            scheduler.deleteJob(jobKey);

        } catch (SchedulerException e) {
            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
        }

    }
}

