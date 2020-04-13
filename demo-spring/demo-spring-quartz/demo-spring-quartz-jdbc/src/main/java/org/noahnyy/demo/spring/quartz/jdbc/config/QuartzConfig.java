package org.noahnyy.demo.spring.quartz.jdbc.config;

import org.noahnyy.demo.spring.quartz.jdbc.job.TestJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author niuyy
 * @since 2020/4/11
 */
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail testJob() {
        return JobBuilder.newJob(TestJob.class).withIdentity("testJob").storeDurably().build();
    }

    @Bean
    public Trigger testJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(5)
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(testJob())
                .withIdentity("testJob")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
