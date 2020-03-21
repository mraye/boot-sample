package com.github.vspro.bootquartz.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class SchedulerBeanPostProcessor implements BeanPostProcessor {


    private final Log logger = LogFactory.getLog(getClass());


    @Autowired
    private Scheduler scheduler;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        try {
            if (bean instanceof AbstractQuartzJobBean) {
                AbstractQuartzJobBean jobBean = (AbstractQuartzJobBean) bean;

                if (jobBean.needStarts()) {

                    String jobName = jobBean.getClass().getSimpleName();
                    JobDetail jobDetail = JobBuilder.newJob(jobBean.getClass())
                            .withDescription(jobName)
                            .withIdentity(jobName, jobBean.getGroupName()).build();

                    CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobBean.getGroupName())
                            .withSchedule(CronScheduleBuilder.cronSchedule(jobBean.getCronExpression()))
                            .build();
                    scheduler.scheduleJob(jobDetail, cronTrigger);
                }
            }
        } catch (SchedulerException e) {
            logger.error("scheduling a job error ", e);
        }
        return bean;
    }
}
