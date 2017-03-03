package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Unit;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class UnitRepository extends EntityRepository<Unit> {
    public UnitRepository() {
        super(Unit.class);
    }

    public Unit getUnitByMetricSpecId(long homeId, long specId){
        Query query = getManager().createQuery("select u from Unit u inner join MetricSpec ms on u.unitId=ms.unit.unitId inner join Metric m on ms.specId = m.metricSpec.specId where (ms.specId = :specId and m.smartHome.smartHomeId = :homeId)");
        query.setParameter("homeId", homeId);
        query.setParameter("specId", specId);
        List<Unit> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
