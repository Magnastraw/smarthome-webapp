package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.HomeParam;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class HomeParamRepository extends EntityRepository<HomeParam> {
    public HomeParamRepository() {
        super(HomeParam.class);
    }

    @Override
    public List<HomeParam> getAll() {
        Query query = getManager().createQuery("select hp from HomeParam hp order by hp.paramId");
        return query.getResultList();
    }
}
