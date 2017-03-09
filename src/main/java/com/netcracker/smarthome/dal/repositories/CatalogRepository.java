package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Catalog;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Repository
public class CatalogRepository extends EntityRepository<Catalog> {
    public CatalogRepository() {
        super(Catalog.class);
    }

    public List<Catalog> getSubcatalogs(Catalog catalog) {
        Query query = getManager().createQuery("select subc from Catalog subc join Catalog c on subc.parentCatalog.catalogId=c.catalogId where c.catalogId=:parentId");
        query.setParameter("parentId", catalog.getCatalogId());
        return query.getResultList();
    }

    public List<Catalog> getSubcatalogsRecursively(Catalog rootCatalog) {
        List<Catalog> listCatalogs = getSubcatalogs(rootCatalog), tmp;
        Queue<Catalog> queueCatalogs = new LinkedList<Catalog>();
        queueCatalogs.addAll(listCatalogs);
        while (!queueCatalogs.isEmpty()) {
            tmp = getSubcatalogs(queueCatalogs.poll());
            listCatalogs.addAll(tmp);
            queueCatalogs.addAll(tmp);
        }
        return listCatalogs;
    }

    public List<Catalog> getObjectsCatalogs(long homeId){
        Query query = getManager().createQuery("select catalog from Catalog catalog inner join SmartObject smartObject on catalog.catalogId=smartObject.catalog.catalogId where smartObject.smartHome.smartHomeId=:homeId group by catalog.catalogId");
        query.setParameter("homeId", homeId);
        return query.getResultList();
    }

    public List<Catalog> getRootMetricSpecsCatalogs(long smartHomeId) {
        Query query = getManager().createQuery("select c from Catalog c where c.parentCatalog.catalogId=:catalogId order by c.catalogName");
        query.setParameter("catalogId", getRootCatalogId("metricSpecsRootCatalog", smartHomeId));
        return query.getResultList();
    }

    public long getRootCatalogId(String catalogName, long smartHomeId) {
        Query query = getManager().createQuery("select c.catalogId from Catalog c where c.parentCatalog.catalogId is null and c.smartHome.smartHomeId = :smartHomeId and c.catalogName=:catalogName ");
        query.setParameter("smartHomeId", smartHomeId);
        query.setParameter("catalogName", catalogName);
        return Long.parseLong(query.getResultList().get(0).toString());
    }

    public List<Catalog> getRootAlarmSpecsCatalogs(long smartHomeId) {
        Query query = getManager().createQuery("select c from Catalog c where c.parentCatalog.catalogId=:catalogId order by c.catalogName");
        query.setParameter("catalogId", getRootCatalogId("alarmSpecsRootCatalog", smartHomeId));
        return query.getResultList();
    }

    public boolean checkCatalogName(String catalogName, long parentCatalogId) {
        Query query = getManager().createQuery("select c from Catalog c where c.parentCatalog.catalogId=:catalogId and c.catalogName = :catalogName");
        query.setParameter("catalogId", parentCatalogId);
        query.setParameter("catalogName", catalogName);
        if (query.getResultList().size() != 0)
            return false;
        else
            return true;
    }

    public List<Catalog> getPathToCatalog(Catalog catalog) {
        Query query = getManager().createNativeQuery("with recursive rec (catalog_id, catalog_name, parent_catalog_id, smart_home_id) " +
                "as (select c1.*, 1 as level from catalogs c1 where c1.catalog_id = :catalogId " +
                "union select c2.*, rec.level+1 as level from catalogs c2 " +
                "inner join rec on (rec.parent_catalog_id = c2.catalog_id)) " +
                "select * from rec  order by level desc", Catalog.class)
                .setParameter("catalogId", catalog.getCatalogId());
        List<Catalog> path = query.getResultList();
        path.remove(0);
        return path;
    }
}
