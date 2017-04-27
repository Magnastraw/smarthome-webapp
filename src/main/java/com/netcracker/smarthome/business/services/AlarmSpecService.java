package com.netcracker.smarthome.business.services;

import com.netcracker.smarthome.dal.repositories.AlarmSpecRepository;
import com.netcracker.smarthome.model.entities.Catalog;
import com.netcracker.smarthome.model.entities.AlarmSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AlarmSpecService {
    private final AlarmSpecRepository alarmSpecRepository;

    @Autowired
    public AlarmSpecService(AlarmSpecRepository alarmSpecRepository) {
        this.alarmSpecRepository = alarmSpecRepository;
    }

    @Transactional
    public void deleteAlarmSpec(Object primaryKey) {
        alarmSpecRepository.delete(primaryKey);
    }

    @Transactional(readOnly = true)
    public List<AlarmSpec> getAlarmSpecs(Catalog catalog) {
        return alarmSpecRepository.getAlarmSpecs(catalog);
    }

    @Transactional
    public void createAlarmSpec(AlarmSpec alarmSpec) {
        alarmSpecRepository.save(alarmSpec);
    }

    @Transactional
    public AlarmSpec updateAlarmSpec(AlarmSpec alarmSpec) {
        return alarmSpecRepository.update(alarmSpec);
    }

    @Transactional(readOnly = true)
    public boolean checkAlarmSpecName(String specName, long catalogId) {
        return this.alarmSpecRepository.checkAlarmSpecName(specName, catalogId);
    }

    @Transactional(readOnly = true)
    public AlarmSpec getAlarmSpec(long specId) {
        return alarmSpecRepository.get(specId);
    }

}
