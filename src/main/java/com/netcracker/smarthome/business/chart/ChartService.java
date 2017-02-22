package com.netcracker.smarthome.business.chart;

import com.netcracker.smarthome.dal.repositories.MetricHistoryRepository;
import com.netcracker.smarthome.dal.repositories.MetricSpecRepository;
import com.netcracker.smarthome.dal.repositories.SmartObjectRepository;
import com.netcracker.smarthome.dal.repositories.UnitRepository;
import com.netcracker.smarthome.model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public List<MetricHistory> getMetricsHistory(){
        return metricHistoryRepository.getMetricsHistory();
    }

    public List<MetricHistory> getMetricsHistoryByObjectId(long id){
        return metricHistoryRepository.getMetricHistoryByObjectId(id);
    }

    public List<MetricHistory> getMetricsHistoryBySpecId(long id){
        return metricHistoryRepository.getMetricHistoryBySpecId(id);
    }

    public Metric getMetricById(long id){
        return metricHistoryRepository.getMetricById(id);
    }

    public MetricSpec getMetricSpecById(long id){
        return metricSpecRepository.getMetricSpecById(id);
    }

    public List<Object[]>  getMetricsHistoryBySpecIdObjectId(long userId,long homeId, long specId, long objectId, int rownum){
        return metricHistoryRepository.getMetricsHistoryBySpecIdObjectId(userId,homeId, specId, objectId, rownum);
    }

    public List<MetricSpec> getMetricsSpecByObjectId(long userId,long smartHomeId, long objectId){
        return metricSpecRepository.getSpecByObjectId(userId,smartHomeId,objectId);
    }

    public List<SmartObject> getSmartObjectBySpecId(long userId,long smartHomeId,long specId){
        return smartObjectRepository.getObjectBySpecId(userId,smartHomeId,specId);
    }

    public Unit getUnitBySpecId(long userId, long homeId, long specId){
        return unitRepository.getUnitByMetricSpecId(userId,homeId,specId);
    }

    public SmartObject getObjectById(long objectId){
        return smartObjectRepository.getObjectById(objectId);
    }

    public Object[] getMetricHistoryBySpecIdObjectId(long userId,long homeId, long specId, long objectId, int ronwum){
        return metricHistoryRepository.getMetricHistoryBySpecIdObjectId(userId, homeId, specId, objectId, ronwum);
    }

}
