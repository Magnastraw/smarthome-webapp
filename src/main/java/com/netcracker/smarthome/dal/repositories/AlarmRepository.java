package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Alarm;
import com.netcracker.smarthome.model.entities.Event;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.*;

@Repository
public class AlarmRepository extends EntityRepository<Alarm> {
    public AlarmRepository() {
        super(Alarm.class);
    }

    public List<Alarm> getChildrenAlarms(Alarm alarm) {
        Query query = getManager().createQuery("select al from Alarm al where al.parentAlarm.alarmId = :alarmId order by al.alarmName");
        query.setParameter("alarmId", alarm.getAlarmId());
        return query.getResultList();
    }

    @Transactional
    public List<Alarm> getChildrenAlarmsRecursively(Alarm rootAlarm) {
        List<Alarm> listAlarms = getChildrenAlarms(rootAlarm), tmp;
        Queue<Alarm> queueAlarms = new LinkedList<Alarm>();
        queueAlarms.addAll(listAlarms);
        while (!queueAlarms.isEmpty()) {
            tmp = getChildrenAlarms(queueAlarms.poll());
            listAlarms.addAll(tmp);
            queueAlarms.addAll(tmp);
        }
        return listAlarms;
    }
}
