package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Alarm;
import com.netcracker.smarthome.model.entities.SmartObject;
import org.springframework.stereotype.Repository;
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

    public List<Alarm> getChildAlarms(Alarm alarm) {
        Query query = getManager().createQuery("select al from Alarm al where al.parentAlarm.alarmId = :alarmId order by al.alarmName");
        query.setParameter("alarmId", alarm.getAlarmId());
        return query.getResultList();
    }

    public List<Alarm> getChildAlarmsRecursively(Alarm rootAlarm) {
        List<Alarm> listAlarms = getChildAlarms(rootAlarm), tmp;
        Queue<Alarm> queueAlarms = new LinkedList<Alarm>();
        queueAlarms.addAll(listAlarms);
        while (!queueAlarms.isEmpty()) {
            tmp = getChildAlarms(queueAlarms.poll());
            listAlarms.addAll(tmp);
            queueAlarms.addAll(tmp);
        }
        return listAlarms;
    }

    public List<Alarm> getRootAlarms(long smartHomeId) {
        Query query = getManager().createQuery("select al from Alarm al where al.parentAlarm.alarmId is null and al.object.smartHome.smartHomeId = :smartHomeId order by al.alarmName");
        query.setParameter("smartHomeId", smartHomeId);
        return query.getResultList();
    }

    public List<Alarm> getPathToAlarm(Alarm alarm) {
        Query query = getManager().createNativeQuery("with recursive rec " +
                "as (select a1.*, 1 as level from alarms a1 where a1.alarm_id = :alarm_id " +
                "union select a2.*, rec.level+1 as level from alarms a2 " +
                "inner join rec on (rec.parent_alarm_id = a2.alarm_id)) " +
                "select * from rec  order by level desc", Alarm.class)
                .setParameter("alarm_id", alarm.getAlarmId());
        return query.getResultList();
    }

    public Alarm getAlarm(long objectId, Long subobjectId, long specId) {
        Query query;
        if (subobjectId != null) {
            query = getManager().createQuery("select a from Alarm a where " +
                    "(a.object.smartObjectId = :objectId and " +
                    "a.subobject.smartObjectId = :subobjectId and " +
                    "a.alarmSpec.specId = :specId)");
            query.setParameter("objectId", objectId);
            query.setParameter("subobjectId", subobjectId);
            query.setParameter("specId", specId);
        }
        else {
            query = getManager().createQuery("select a from Alarm a where " +
                    "(a.object.smartObjectId = :objectId and " +
                    "a.subobject.smartObjectId is null and " +
                    "a.alarmSpec.specId = :specId)");
            query.setParameter("objectId", objectId);
            query.setParameter("specId", specId);
        }
        List<Alarm> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public List<Alarm> getAlarmsByObject(long objectId) {
        Query query = getManager().createQuery("select a from Alarm a where " +
                "(a.object.smartObjectId = :objectId or " +
                "a.subobject.smartObjectId = :objectId) and " +
                "(a.severity > 1)");
        query.setParameter("objectId", objectId);
        return query.getResultList();
    }
}
