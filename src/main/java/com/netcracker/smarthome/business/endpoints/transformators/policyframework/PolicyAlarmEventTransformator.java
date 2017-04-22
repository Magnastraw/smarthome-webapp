package com.netcracker.smarthome.business.endpoints.transformators.policyframework;

import com.netcracker.smarthome.business.endpoints.jsonentities.JsonAlarm;
import com.netcracker.smarthome.business.endpoints.services.SmartObjectService;
import com.netcracker.smarthome.business.endpoints.transformators.ITransformator;
import com.netcracker.smarthome.business.policy.events.AlarmEvent;
import com.netcracker.smarthome.business.policy.events.EventType;
import com.netcracker.smarthome.business.specs.AlarmSpecService;
import com.netcracker.smarthome.model.enums.AlarmSeverity;
import org.springframework.beans.factory.annotation.Autowired;

public class PolicyAlarmEventTransformator implements ITransformator<AlarmEvent,JsonAlarm> {
    private final SmartObjectService smartObjectService;
    private final AlarmSpecService alarmSpecService;

    @Autowired
    public PolicyAlarmEventTransformator(SmartObjectService smartObjectService, AlarmSpecService alarmSpecService) {
        this.smartObjectService = smartObjectService;
        this.alarmSpecService = alarmSpecService;
    }

    public AlarmEvent fromJsonEntity(JsonAlarm jsonEntity) {
        AlarmEvent alarmEvent = new AlarmEvent();
        alarmEvent.setObject(smartObjectService.getObjectByExternalKey(jsonEntity.getSmartHomeId(), jsonEntity.getObjectId()));
        alarmEvent.setSubobject(smartObjectService.getObjectByExternalKey(jsonEntity.getSmartHomeId(), jsonEntity.getSubobjectId()));
        alarmEvent.setSeverity(AlarmSeverity.valueOf(jsonEntity.getSeverity().toUpperCase()));
        alarmEvent.setSpec(alarmSpecService.getAlarmSpec(jsonEntity.getSpecId()));
        alarmEvent.setType(EventType.ALARM);
        //alarmEvent.setRegistryDate(jsonEntity.getRegistryDate());
        return alarmEvent;
    }

    public JsonAlarm toJsonEntity(AlarmEvent Entity) {
        throw new UnsupportedOperationException("Not supported");
    }
}
