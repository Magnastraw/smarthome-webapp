package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Unit;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.Query;
import java.util.List;

@Repository
public class UnitRepository extends EntityRepository<Unit> {
    public UnitRepository() {
        super(Unit.class);
    }

    public Unit getUnitByMetricSpecId(long specId){
        Query query = getManager().createQuery("select u from Unit u inner join MetricSpec ms on u.unitId=ms.unit.unitId  where (ms.specId = :specId )");
        query.setParameter("specId", specId);
        List<Unit> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
	}
	
    public List<Unit> getUnits() {
        Query query = getManager().createQuery("select u from Unit u order by u.unitName");
        return query.getResultList();
    }

    public boolean checkUnitName(String unitName) {
        Query query = getManager().createQuery("select u from Unit u where u.unitName=:unitName");
        query.setParameter("unitName", unitName);
        if (query.getResultList().size() != 0)
            return false;
        else
            return true;
    }

    public List<BigInteger> getBaseFactors() {
        Query query = getManager().createQuery("select distinct u.baseFactor from Unit u order by u.baseFactor");
        return query.getResultList();
    }
}
