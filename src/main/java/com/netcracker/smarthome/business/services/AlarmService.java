package com.netcracker.smarthome.business.services;

import com.netcracker.smarthome.dal.repositories.AlarmRepository;
import com.netcracker.smarthome.model.entities.Alarm;
import com.netcracker.smarthome.model.enums.AlarmSeverity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlarmService {
    private final AlarmRepository alarmRepository;

    @Autowired
    public AlarmService(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    @Transactional(readOnly = true)
    public List<Alarm> getChildrenAlarms(Alarm alarm) {
        return alarmRepository.getChildrenAlarms(alarm);
    }
    
    @Transactional
    public void deleteAlarm(Object primaryKey) {
        alarmRepository.delete(primaryKey);
    }
    
    @Transactional(readOnly = true)
    public Alarm getAlarmById(long alarmId) {
        return alarmRepository.get(alarmId);
    }
    
    @Transactional
    public void createAlarm(Alarm alarm) {
        alarmRepository.save(alarm);
    }

    /*@Transactional
    public boolean checkAlarmName(String alarmName, long parentAlarmId) {
        return alarmRepository.checkAlarmName(alarmName, parentAlarmId);
    }*/

    @Transactional(readOnly = true)
    public List<Alarm> getPathToAlarm(Alarm alarm) {
        return alarmRepository.getPathToAlarm(alarm);
    }

    @Transactional(readOnly = true)
    public List<Alarm> getChildrenAlarmsRecursively(Alarm rootAlarm) {
        return alarmRepository.getChildrenAlarmsRecursively(rootAlarm);
    }

    @Transactional
    public Alarm updateCatalog(Alarm alarm) {
        return alarmRepository.update(alarm);
    }

    @Transactional(readOnly = true)
    public List<Alarm> getRootAlarms(long smartHomeId) {
        return alarmRepository.getRootAlarms(smartHomeId);
    }

    @Transactional(readOnly = true)
    public List<Alarm> getRootAlarmsByObject(long objectId) {
        return alarmRepository.getRootAlarmsByObject(objectId);
    }

    @Transactional(readOnly = true)
    public AlarmSeverity getMaxSeverity(long objectId) {
        return alarmRepository.getMaxSeverity(objectId);
    }
}
