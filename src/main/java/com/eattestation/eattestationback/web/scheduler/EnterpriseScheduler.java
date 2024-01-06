package com.eattestation.eattestationback.web.scheduler;

import com.eattestation.eattestationback.web.service.EnterpriseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@EnableScheduling
@Component
public class EnterpriseScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnterpriseScheduler.class);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    EnterpriseService enterpriseService;

    public EnterpriseScheduler(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @Value("${scheduler.enterprise_refresh.enable}")
    private boolean enableScheduler;

    @Scheduled(cron = "${scheduler.enterprise_refresh.cron.expression}")
    public void scheduleRefreshEmployee() {

        if (!enableScheduler) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Scheduler is deactivated :: Execution Time - {}", dateFormat.format(new Date()));
            }
            return;
        }

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Enterprise refresh scheduler :: Execution Time - {}", dateFormat.format(new Date()));
        }

        enterpriseService.askForDocuments();
    }
}
