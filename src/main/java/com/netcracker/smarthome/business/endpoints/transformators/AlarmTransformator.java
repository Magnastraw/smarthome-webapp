package com.netcracker.smarthome.business.endpoints.transformators;

import com.netcracker.smarthome.business.endpoints.jsonentities.JsonAlarm;
import com.netcracker.smarthome.business.endpoints.services.SmartObjectService;
import com.netcracker.smarthome.business.specs.AlarmSpecService;
import com.netcracker.smarthome.model.entities.Alarm;
import com.netcracker.smarthome.model.enums.AlarmSeverity;
import org.springframework.beans.factory.annotation.Autowired;

public class AlarmTransformator implements ITransformator<Alarm,JsonAlarm>  {
    private final SmartObjectService smartObjectService;
    private final AlarmSpecService alarmSpecService;

    @Autowired
    public AlarmTransformator(SmartObjectService smartObjectService, AlarmSpecService alarmSpecService) {
        this.smartObjectService = smartObjectService;
        this.alarmSpecService = alarmSpecService;
    }

    public Alarm fromJsonEntity(JsonAlarm jsonEntity) {
        Alarm alarm = new Alarm();
        alarm.setObject(smartObjectService.getObjectByExternalKey(jsonEntity.getSmartHomeId(), jsonEntity.getObjectId()));
        alarm.setSubobject(smartObjectService.getObjectByExternalKey(jsonEntity.getSmartHomeId(), jsonEntity.getSubobjectId()));
        //alarm.setStartTime(jsonEntity.getRegistryDate());
        alarm.setSeverity(AlarmSeverity.valueOf(jsonEntity.getSeverity().toUpperCase()));
        alarm.setAlarmSpec(alarmSpecService.getAlarmSpec(jsonEntity.getSpecId()));
        alarm.setAlarmDescription(jsonEntity.getAlarmParameters());
        return alarm;
    }

    public JsonAlarm toJsonEntity(Alarm Alarm) {
        throw new UnsupportedOperationException("Not supported");
    }
}
