package com.netcracker.smarthome.business.services;

import com.netcracker.smarthome.dal.repositories.AlarmRepository;
import com.netcracker.smarthome.dal.repositories.AlarmSpecRepository;
import com.netcracker.smarthome.dal.repositories.UserRepository;
import com.netcracker.smarthome.model.entities.Alarm;
import com.netcracker.smarthome.model.entities.AlarmSpec;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.model.enums.AlarmSeverity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;
    private final AlarmSpecRepository alarmSpecRepository;

    @Autowired
    public AlarmService(AlarmRepository alarmRepository, UserRepository userRepository, AlarmSpecRepository alarmSpecRepository) {
        this.alarmRepository = alarmRepository;
        this.userRepository = userRepository;
        this.alarmSpecRepository = alarmSpecRepository;
    }

    @Transactional(readOnly = true)
    public List<Alarm> getChildrenAlarms(Alarm alarm) {
        return alarmRepository.getChildAlarms(alarm);
    }

    @Transactional
    public void deleteAlarm(Object primaryKey) {
        alarmRepository.delete(primaryKey);
    }

    @Transactional(readOnly = true)
    public Alarm getAlarm(long alarmId) {
        return alarmRepository.get(alarmId);
    }

    @Transactional(readOnly = true)
    public Alarm getAlarm(SmartObject object, SmartObject subobject, AlarmSpec spec) {
        return alarmRepository.get(object, subobject, spec);
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
        return alarmRepository.getChildAlarmsRecursively(rootAlarm);
    }

    @Transactional(readOnly = true)
    public List<Alarm> getRootAlarms(long smartHomeId) {
        return alarmRepository.getRootAlarms(smartHomeId);
    }

    @Transactional(readOnly = true)
    public List<Alarm> getRootAlarmsByObject(long objectId) {
        return alarmRepository.getRootAlarmsByObject(objectId);
    }

    @Transactional
    public void saveRaisedAlarm(Alarm alarm) {
        Alarm existingAlarm = alarmRepository.get(alarm.getObject(), alarm.getSubobject(), alarm.getAlarmSpec());
        if (existingAlarm != null) {
            alarm.setAlarmId(existingAlarm.getAlarmId());
            if (existingAlarm.getSeverity() != AlarmSeverity.CLEAR) {
                alarm.setStartTime(existingAlarm.getStartTime());
            }
        }
        alarmRepository.update(alarm);
    }

    @Transactional
    public void clearAll(List<Alarm> alarms, long clearedUserId) {
        Timestamp clearTime = Timestamp.valueOf(LocalDateTime.now());
        for (Alarm alarm : alarms)
            clear(alarm, clearedUserId, clearTime);
    }

    @Transactional
    public void clearAlarm(Alarm alarmProps, boolean withChildren, boolean bySystem) {
        Alarm existingAlarm = alarmRepository.get(alarmProps.getObject(), alarmProps.getSubobject(), alarmProps.getAlarmSpec());
        if (existingAlarm == null)
            return;
        long clearedUserId = bySystem ? userRepository.getByEmail("system@system.com").getUserId() : alarmProps.getClearedUserId();
        Timestamp clearTime = Timestamp.valueOf(LocalDateTime.now());
        if (withChildren)
            clearWithChildren(existingAlarm, clearedUserId, clearTime);
        else
            clear(existingAlarm, clearedUserId, clearTime);
    }

    private void clear(Alarm alarm, long clearedUserId, Timestamp clearTime) {
        alarm.setClearedUserId(clearedUserId);
        alarm.setSeverity(AlarmSeverity.CLEAR);
        alarm.setEndTime(clearTime);
        alarmRepository.update(alarm);
    }

    private void clearWithChildren(Alarm alarmToClear, long clearedUserId, Timestamp clearTime) {
        List<Alarm> alarmWithChildren = new LinkedList<Alarm>();
        alarmWithChildren.add(alarmToClear);
        alarmWithChildren.addAll(alarmRepository.getChildAlarmsRecursively(alarmToClear));
        for (Alarm alarm : alarmWithChildren)
            clear(alarm, clearedUserId, clearTime);
    }

    @Transactional(readOnly = true)
    public AlarmSpec getSpecById(long specId) {
        return alarmSpecRepository.get(specId);
    }
}
