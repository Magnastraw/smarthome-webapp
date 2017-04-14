package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.AlarmSpec;
import com.netcracker.smarthome.model.entities.Catalog;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import java.util.List;

@Repository
public class AlarmSpecRepository extends EntityRepository<AlarmSpec> {
    public AlarmSpecRepository() {
        super(AlarmSpec.class);
    }

    public List<AlarmSpec> getAlarmSpecs(Catalog catalog) {
        Query query = getManager().createQuery("select al from AlarmSpec al where al.catalog.catalogId = :catalogId order by al.specName");
        query.setParameter("catalogId", catalog.getCatalogId());
        return query.getResultList();
    }

    public boolean checkAlarmSpecName(String specName, long catalogId) {
        Query query = getManager().createQuery("select al from AlarmSpec al where al.catalog.catalogId=:catalogId and al.specName = :specName");
        query.setParameter("catalogId", catalogId);
        query.setParameter("specName", specName);
        List<AlarmSpec> result = query.getResultList();
        return result.isEmpty() ? true : false;
    }
}
