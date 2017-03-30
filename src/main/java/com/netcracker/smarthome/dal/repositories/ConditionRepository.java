package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Condition;
import com.netcracker.smarthome.model.entities.ConditionParam;
import com.netcracker.smarthome.model.entities.Rule;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ConditionRepository extends EntityRepository<Condition> {
    public ConditionRepository() {
        super(Condition.class);
    }

    public List<ConditionParam> getConditionParams(Condition condition) {
        Query query = getManager().createQuery("select cp from ConditionParam cp where cp.condition.nodeId = :conditionId");
        query.setParameter("conditionId", condition.getNodeId());
        return query.getResultList();
    }

    public Condition getRootCondition(Rule rule) {
        Query query = getManager().createQuery("select c from Condition c where c.rule.ruleId = :ruleId and c.parentNode = null");
        query.setParameter("ruleId", rule.getRuleId());
        List<Condition> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
