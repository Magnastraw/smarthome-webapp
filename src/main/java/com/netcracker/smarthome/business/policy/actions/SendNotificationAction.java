package com.netcracker.smarthome.business.policy.actions;

import com.netcracker.smarthome.ApplicationContextHolder;
import com.netcracker.smarthome.business.policy.events.PolicyEvent;
import com.netcracker.smarthome.business.services.AlarmService;
import com.netcracker.smarthome.business.services.MetricService;
import com.netcracker.smarthome.business.services.NotificationService;
import com.netcracker.smarthome.model.entities.AlarmSpec;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.enums.Channel;
import com.netcracker.smarthome.model.interfaces.NotificationObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import java.util.Map;

public class SendNotificationAction implements PolicyAction {
    private static final Logger LOG = LoggerFactory.getLogger(SendNotificationAction.class);
    private String message;
    private Channel preferredChannel;

    private NotificationService notificationService;
    private AlarmService alarmService;
    private MetricService metricService;

    public SendNotificationAction(Map<String, String> params) {
        initBeans();
        this.message = params.get("message");
        this.preferredChannel = params.containsKey("preferredChannel") ? Channel.valueOf(StringUtils.capitalize(params.get("preferredChannel").toLowerCase())) : null;
    }

    private void initBeans() {
        ApplicationContext context = ApplicationContextHolder.getApplicationContext();
        this.notificationService = context.getBean(NotificationService.class);
        this.alarmService = context.getBean(AlarmService.class);
        this.metricService = context.getBean(MetricService.class);
    }

    public void execute(PolicyEvent causalEvent) {
        String messageToSend = String.format("%s\n--\nCausal event description:\n%s", message, causalEvent.toString());
        try {
            if (preferredChannel != null)
                notificationService.sendNotification(messageToSend, causalEvent.getObject().getSmartHome(), preferredChannel, getNotificationObject(causalEvent));
            else
                notificationService.sendNotification(messageToSend, causalEvent.getObject().getSmartHome(), getNotificationObject(causalEvent));
        } catch(Exception e) {
            LOG.info("Error during notification sending");
        }
    }

    private NotificationObject getNotificationObject(PolicyEvent event) {
        switch (event.getType()) {
            case EVENT:
                return event.getDbEvent();
            case ALARM:
                return alarmService.getAlarm(event.getObject(), event.getSubobject(), (AlarmSpec) event.getSpec());
            case METRIC:
                return metricService.getMetric(event.getObject(), event.getSubobject(), (MetricSpec) event.getSpec());
            default:
                return null;
        }
    }
}
