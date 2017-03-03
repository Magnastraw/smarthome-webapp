package com.netcracker.smarthome.business.chart;

import com.netcracker.smarthome.dal.repositories.*;
import com.netcracker.smarthome.model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service("ChartService")
@Transactional(readOnly = true)
public class ChartService {

    @Autowired
    private MetricHistoryRepository metricHistoryRepository;

    @Autowired
    private SmartObjectRepository smartObjectRepository;

    @Autowired
    private MetricSpecRepository metricSpecRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private UserRepository userRepository;

    public List<SmartObject> getSmartObjectByIds(ArrayList<Long> smartObjectIds){
        ArrayList<SmartObject> smartObjects = new ArrayList<SmartObject>();
        for(Long objectId:smartObjectIds){
            smartObjects.add(smartObjectRepository.get(objectId));
        }
        return smartObjects;
    }

    public List<MetricSpec> getMetricSpecByIds(ArrayList<Long> metricSpecIds){
        ArrayList<MetricSpec> metricSpecObjects = new ArrayList<MetricSpec>();
        for(Long objectId:metricSpecIds){
            metricSpecObjects.add(metricSpecRepository.get(objectId));
        }
        return metricSpecObjects;
    }


    public List<SmartObject> getSmartObject() {
        return smartObjectRepository.getAll();
    }

    public List<MetricSpec> getMetricSpec(){
        return metricSpecRepository.getAll();
    }


    public List<SmartObject> getSmartObjectByHomeId(long homeId) {
        return smartObjectRepository.getObjectByHomeId(homeId);
    }

    public List<MetricSpec> getMetricSpecByHomeId(long specId) {
        return metricSpecRepository.getMetricSpecByHomeId(specId);
    }

    public List<MetricHistory> getMetricsHistory() {
        return metricHistoryRepository.getMetricsHistory();
    }

    public List<MetricHistory> getMetricsHistoryByObjectId(long id) {
        return metricHistoryRepository.getMetricHistoryByObjectId(id);
    }

    public List<MetricHistory> getMetricsHistoryBySpecId(long id) {
        return metricHistoryRepository.getMetricHistoryBySpecId(id);
    }

    public Metric getMetricById(long id) {
        return metricHistoryRepository.getMetricById(id);
    }

    public MetricSpec getMetricSpecById(long id) {
        return metricSpecRepository.getMetricSpecById(id);
    }

    public List<Object[]> getMetricsHistoryBySpecIdObjectId(long homeId, long specId, long objectId) {
        return metricHistoryRepository.getMetricsHistoryBySpecIdObjectId(homeId, specId, objectId);
    }

    public List<MetricSpec> getMetricsSpecByObjectId(long userId, long smartHomeId, long objectId) {
        return metricSpecRepository.getSpecByObjectId(userId, smartHomeId, objectId);
    }

    public List<SmartObject> getSmartObjectBySpecId(long smartHomeId, long specId) {
        return smartObjectRepository.getObjectBySpecId( smartHomeId, specId);
    }

    public Unit getUnitBySpecId( long homeId, long specId) {
        return unitRepository.getUnitByMetricSpecId(homeId, specId);
    }

    public SmartObject getObjectById(long objectId) {
        return smartObjectRepository.getObjectById(objectId);
    }

    public Object[] getMetricHistoryBySpecIdObjectId(long userId, long homeId, long specId, long objectId, int ronwum) {
        return metricHistoryRepository.getMetricHistoryBySpecIdObjectId(userId, homeId, specId, objectId, ronwum);
    }

}
