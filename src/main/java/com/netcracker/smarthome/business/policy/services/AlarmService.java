package com.netcracker.smarthome.business.policy.services;

import com.netcracker.smarthome.dal.repositories.AlarmRepository;
import com.netcracker.smarthome.model.entities.Alarm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlarmService {
    private final AlarmRepository alarmRepository;

    @Autowired
    public AlarmService(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    @Transactional
    public void deleteAlarm(long alarmId) {
        alarmRepository.delete(alarmId);
    }

    @Transactional
    public void createAlarm(Alarm alarm) {
        alarmRepository.save(alarm);
    }

    public Alarm updateAlarm(Alarm alarm) {
        return alarmRepository.update(alarm);
    }
}
