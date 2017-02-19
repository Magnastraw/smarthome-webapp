package com.netcracker.smarthome.business.chart;

import com.netcracker.smarthome.dal.repositories.MetricHistoryRepository;
import com.netcracker.smarthome.dal.repositories.SmartObjectRepository;
import com.netcracker.smarthome.model.entities.Metric;
import com.netcracker.smarthome.model.entities.MetricHistory;
import com.netcracker.smarthome.model.entities.SmartObject;
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

    public List<MetricHistory> getMetricsHistoryBySpecIdObjectID(long specId, long objectId){
        return metricHistoryRepository.getMetricHistoryBySpecIdObjectId(specId, objectId);
    }

    public List<SmartObject> getSmartObjectBySpecId(long specId){
        return smartObjectRepository.getObjectBySpecId(specId);
    }

    @Transactional(readOnly = false)
    public void addMetricHistory(MetricHistory metricHistory) throws ParseException {
        metricHistoryRepository.save(metricHistory);
    }

}
