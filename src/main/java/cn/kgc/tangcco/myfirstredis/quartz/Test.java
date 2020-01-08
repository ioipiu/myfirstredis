package cn.kgc.tangcco.myfirstredis.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import java.text.ParseException;


/**
 * @program: myfirstredis
 * @description: TODO
 * @author: cuihao
 * @create: 2020-01-07 09:48
 * @version: V1.0
 **/
public class Test {
    public static void main(String[] args) throws SchedulerException, ParseException {
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity("myJob").build();
        //SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger").startAt(new Date(System.currentTimeMillis()+2000)).withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).endAt(new Date(System.currentTimeMillis()+20000)).build();
//        SimpleTriggerImpl simpleTrigger = new SimpleTriggerImpl("aaa");
//        simpleTrigger.setStartTime(new Date(System.currentTimeMillis()+2000));
//        simpleTrigger.setEndTime(new Date(System.currentTimeMillis()+20000));
        CronTrigger cronTrigger = new CronTriggerImpl("t2","aaa","0 0/1 * * * ?");
        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.scheduleJob(jobDetail,cronTrigger);
        scheduler.start();

    }
}
