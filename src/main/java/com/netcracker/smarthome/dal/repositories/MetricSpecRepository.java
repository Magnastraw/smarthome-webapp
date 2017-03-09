package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Catalog;
import com.netcracker.smarthome.model.entities.MetricSpec;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class MetricSpecRepository extends EntityRepository<MetricSpec> {
    public MetricSpecRepository() {
        super(MetricSpec.class);
    }

    public List<MetricSpec> getSpecByObjectId(long objectId) {
        Query query = getManager().createQuery("select ms from MetricSpec ms inner join Metric m on m.metricSpec.specId  = ms.specId inner join SmartObject so on m.object.smartObjectId = so.smartObjectId where (so.smartObjectId = :objectId ) group by ms.specId");
        query.setParameter("objectId", objectId);
        return query.getResultList();
    }

    public List<MetricSpec> getMetricSpecByHomeId(long homeId){
        Query query = getManager().createQuery("select metricSpec from MetricSpec metricSpec inner join Metric metric on metric.metricSpec.specId=metricSpec.specId where ( metric.smartHome.smartHomeId = :homeId) group by metricSpec.specId ");
        query.setParameter("homeId", homeId);
        return query.getResultList();
    }
	
    public List<MetricSpec> getMetricSpecs(Catalog catalog) {
        Query query = getManager().createQuery("select ms from MetricSpec ms where ms.catalog.catalogId = :catalogId order by ms.specName");
        query.setParameter("catalogId", catalog.getCatalogId());
        return query.getResultList();
    }

    public boolean checkMetricSpecName(String specName, long catalogId) {
        Query query = getManager().createQuery("select ms from MetricSpec ms where ms.catalog.catalogId=:catalogId and ms.specName = :specName");
        query.setParameter("catalogId", catalogId);
        query.setParameter("specName", specName);
        if (query.getResultList().size() != 0)
            return false;
        else
            return true;
    }
}
