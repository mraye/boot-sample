package com.github.vspro.bootquartz.job;

import com.github.vspro.bootquartz.config.AbstractQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;


@Component
public class HelloWorldJob extends AbstractQuartzJobBean {


    @Override
    protected void doExecuteInternal(JobExecutionContext context) {
        System.out.println("hello world boot quartz...");

    }

    @Override
    protected String getCronExpression() {
        return "0/1 * * * * ? *";
    }

    @Override
    protected Boolean needStarts() {
        return Boolean.TRUE;
    }
}
