package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Action;
import com.netcracker.smarthome.model.entities.ActionParam;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ActionRepository extends EntityRepository<Action> {
    public ActionRepository() {
        super(Action.class);
    }

    public List<ActionParam> getActionParams(Action action) {
        Query query = getManager().createQuery("select ap from ActionParam ap where ap.action.actionId = :actionId");
        query.setParameter("actionId", action.getActionId());
        return query.getResultList();
    }
}
