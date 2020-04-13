package org.noahnyy.demo.spring.quartz.jdbc.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

/**
 * @author niuyy
 * @since 2020/4/11
 */
@Slf4j
public class TestJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("{} -- {} -- {}", this.getClass().getSimpleName(), new SimpleDateFormat().format(new Date()), jobExecutionContext);
    }
}
