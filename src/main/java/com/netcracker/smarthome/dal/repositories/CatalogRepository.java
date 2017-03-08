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
}
