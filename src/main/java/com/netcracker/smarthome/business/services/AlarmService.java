package com.netcracker.smarthome.business.services;

import com.netcracker.smarthome.business.endpoints.TaskManager;
import com.netcracker.smarthome.dal.repositories.AlarmRepository;
import com.netcracker.smarthome.dal.repositories.AlarmSpecRepository;
import com.netcracker.smarthome.dal.repositories.EventHistoryRepository;
import com.netcracker.smarthome.dal.repositories.UserRepository;
import com.netcracker.smarthome.model.entities.Alarm;
import com.netcracker.smarthome.model.entities.AlarmSpec;
import com.netcracker.smarthome.model.entities.EventHistory;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.model.enums.AlarmSeverity;
import com.netcracker.smarthome.model.enums.AlarmSeverity;
import com.netcracker.smarthome.web.common.ContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;
    private final AlarmSpecRepository alarmSpecRepository;
    private final EventHistoryRepository eventHistoryRepository;
    private final TaskManager taskManager;

    @Autowired
    public AlarmService(AlarmRepository alarmRepository, UserRepository userRepository, AlarmSpecRepository alarmSpecRepository, EventHistoryRepository eventHistoryRepository, TaskManager taskManager) {
        this.alarmRepository = alarmRepository;
        this.userRepository = userRepository;
        this.alarmSpecRepository = alarmSpecRepository;
        this.eventHistoryRepository = eventHistoryRepository;
        this.taskManager = taskManager;
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

    @Transactional(readOnly = true)
    public List<Alarm> getPathToAlarm(Alarm alarm) {
        return alarmRepository.getPathToAlarm(alarm);
    }

    @Transactional(readOnly = true)
    public List<Alarm> getChildAlarmsRecursively(Alarm rootAlarm) {
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
    public boolean saveRaisedAlarm(Alarm alarm) {
        EventHistory eventHistory = new EventHistory(alarm.getSeverityChangeTime(), alarm.getEvent(), alarm.getSeverity(), "");
        eventHistoryRepository.save(eventHistory);
        Alarm existingAlarm = alarmRepository.get(alarm.getObject(), alarm.getSubobject(), alarm.getAlarmSpec());
        boolean newAlarm = true;
        if (existingAlarm != null) {
            alarm.setAlarmId(existingAlarm.getAlarmId());
            if (existingAlarm.getSeverity() != AlarmSeverity.CLEAR) {
                alarm.setStartTime(existingAlarm.getStartTime());
                newAlarm = false;
            }
        }
        alarmRepository.update(alarm);
        taskManager.addUpdateEvent(alarm.getEvent().getSmartHome().getSmartHomeId(),"updateAlarm");
        return newAlarm;
    }

    @Transactional
    public List<Alarm> clearAll(List<Alarm> alarms, long clearedUserId) {
        Timestamp clearTime = Timestamp.valueOf(LocalDateTime.now());
        List<Alarm> clearedAlarms = new ArrayList<>();
        for (Alarm alarm : alarms)
            clearedAlarms.add(clear(alarm, clearedUserId, clearTime));
        return clearedAlarms;
    }

    @Transactional
    public boolean clearAlarm(Alarm alarmProps, boolean withChildren, boolean bySystem) {
        Alarm existingAlarm = alarmRepository.get(alarmProps.getObject(), alarmProps.getSubobject(), alarmProps.getAlarmSpec());
        if (existingAlarm == null || existingAlarm.getSeverity() == AlarmSeverity.CLEAR)
            return false;
        long clearedUserId = bySystem ? userRepository.getByEmail("system@system.com").getUserId() : alarmProps.getClearedUserId();
        if (withChildren)
            clearWithChildren(existingAlarm, clearedUserId, alarmProps.getEndTime());
        else
            clear(existingAlarm, clearedUserId, alarmProps.getEndTime());
        return true;
    }

    private Alarm clear(Alarm alarm, long clearedUserId, Timestamp clearTime) {
        EventHistory eventHistory = new EventHistory(clearTime, alarm.getEvent(), AlarmSeverity.CLEAR, "");
        eventHistoryRepository.save(eventHistory);
        alarm.setClearedUserId(clearedUserId);
        alarm.setSeverity(AlarmSeverity.CLEAR);
        alarm.setSeverityChangeTime(clearTime);
        alarm.setEndTime(clearTime);
        return alarmRepository.update(alarm);
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
    @Transactional(readOnly = true)
    public AlarmSeverity getMaxSeverity(long objectId) {
        return alarmRepository.getMaxSeverity(objectId);
    }
}
