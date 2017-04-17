package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Alarm;
import com.netcracker.smarthome.model.entities.SmartObject;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Repository
public class AlarmRepository extends EntityRepository<Alarm> {
    public AlarmRepository() {
        super(Alarm.class);
    }

    public Alarm get(SmartObject object, SmartObject subobject) {
        Query query = getManager().createQuery("select al from Alarm al where al.object = :object and al.subobject=:subobject");
        query.setParameter("object", object);
        query.setParameter("object", subobject);
        List<Alarm> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public List<Alarm> getChildrenAlarms(Alarm alarm) {
        Query query = getManager().createQuery("select al from Alarm al where al.parentAlarm.alarmId = :alarmId order by al.alarmName");
        query.setParameter("alarmId", alarm.getAlarmId());
        return query.getResultList();
    }

    @Transactional
    public List<Alarm> getChildAlarmsRecursively(Alarm rootAlarm) {
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
