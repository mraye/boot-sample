package com.github.vspro.bootquartz.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public abstract class AbstractQuartzJobBean extends QuartzJobBean {

    private final Log logger = LogFactory.getLog(getClass());
    private String groupName = "boot-quartz-group";


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {


        long start = System.currentTimeMillis();
        String jobName = context.getJobDetail().getDescription();

        if (logger.isDebugEnabled()) {
            logger.debug(print("Starting Job >>>>>> %s", jobName));
        }

        try {
            doExecuteInternal(context);
        } catch (Exception e) {
            logger.error(e);
        }

        long up = (System.currentTimeMillis() - start) / 1000;
        if (logger.isDebugEnabled()) {
            logger.debug(print("Finished Job >>>>>> %s , used time %s seconds", jobName, up));
        }

    }

    protected abstract void doExecuteInternal(JobExecutionContext context);


    protected abstract String getCronExpression();


    protected Boolean needStarts() {
        return Boolean.FALSE;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    private String print(String format, Object... objects) {
        return String.format(format, objects);
    }
}
