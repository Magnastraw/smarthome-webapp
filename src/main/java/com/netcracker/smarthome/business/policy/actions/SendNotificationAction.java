package com.netcracker.smarthome.business.policy.actions;

import com.netcracker.smarthome.ApplicationContextHolder;
import com.netcracker.smarthome.business.notification.NotificationService;
import com.netcracker.smarthome.business.policy.events.PolicyEvent;
import com.netcracker.smarthome.business.services.AlarmService;
import com.netcracker.smarthome.business.services.MetricService;
import com.netcracker.smarthome.model.entities.AlarmSpec;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.enums.Channel;
import com.netcracker.smarthome.model.interfaces.NotificationObject;
import org.springframework.context.ApplicationContext;

import java.util.Map;

public class SendNotificationAction implements PolicyAction {
    private String message;
    private Channel preferredChannel;

    private NotificationService notificationService;
    private AlarmService alarmService;
    private MetricService metricService;

    public SendNotificationAction() {
        initBeans();
    }

    public SendNotificationAction(Map<String, String> params) {
        initBeans();
        this.message = params.get("message");
        this.preferredChannel = params.containsKey("preferredChannel") ? Channel.valueOf(params.get("preferredChannel")) : null;
    }

    private void initBeans() {
        ApplicationContext context = ApplicationContextHolder.getApplicationContext();
        this.notificationService = context.getBean(NotificationService.class);
        this.alarmService = context.getBean(AlarmService.class);
        this.metricService = context.getBean(MetricService.class);
    }

    public void execute(PolicyEvent causalEvent) {
        if (preferredChannel != null)
            notificationService.sendNotification(message, causalEvent.getObject().getSmartHome(), preferredChannel, getNotificationObject(causalEvent));
        else
            notificationService.sendNotification(message, causalEvent.getObject().getSmartHome(), getNotificationObject(causalEvent));
    }

    private NotificationObject getNotificationObject(PolicyEvent event) {
        switch (event.getType()) {
            case EVENT:
                return event.getDbEvent();
            case ALARM:
                return alarmService.getAlarm(event.getObject(), event.getSubobject(), (AlarmSpec)event.getSpec());
            case METRIC:
                return metricService.getMetric(event.getObject(), event.getSubobject(), (MetricSpec) event.getSpec(), event.getRegistryDate());
            default:
                return null;
        }
    }
}
