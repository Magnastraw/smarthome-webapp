package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.business.endpoints.IListener;
import com.netcracker.smarthome.business.endpoints.IListenerSupport;
import com.netcracker.smarthome.business.endpoints.ListenersInitializer;
import com.netcracker.smarthome.model.entities.Rule;
import org.springframework.stereotype.Repository;
import java.util.HashSet;
import java.util.Set;

@Repository
public class RuleRepository extends EntityRepository<Rule> implements IListenerSupport<IListener> {
    private Set<IListener> listeners = new HashSet<IListener>();

    public RuleRepository() {
        super(Rule.class);
        initListeners();
    }

    protected void initListeners() {
        listeners = ListenersInitializer.init();
    }

    public void addListener(IListener listener) {
        listeners.add(listener);
    }

    public void removeListener(IListener listener) {
        listeners.remove(listener);
    }

    public void save(Rule rule) {
        super.save(rule);
        onSaveOrUpdate(rule);
    }

    public Rule update(Rule rule) {
        Rule updatedRule = super.update(rule);
        onSaveOrUpdate(updatedRule);
        return updatedRule;
    }

    public void onSaveOrUpdate(Object object) {
        for(IListener listener : listeners) {
            listener.onSaveOrUpdate(null, object);
        }
    }
}
