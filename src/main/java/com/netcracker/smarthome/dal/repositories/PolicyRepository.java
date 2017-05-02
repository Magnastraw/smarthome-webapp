package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.business.endpoints.IListener;
import com.netcracker.smarthome.business.endpoints.IListenerSupport;
import com.netcracker.smarthome.business.endpoints.ListenersInitializer;
import com.netcracker.smarthome.model.entities.Policy;
import org.springframework.stereotype.Repository;
import java.util.HashSet;
import java.util.Set;

@Repository
public class PolicyRepository extends EntityRepository<Policy> implements IListenerSupport<IListener>{
    private Set<IListener> listeners = new HashSet<IListener>();

    public PolicyRepository() {
        super(Policy.class);
        initListeners();
    }

    public void initListeners() {
        listeners = ListenersInitializer.init();
    }

    public void addListener(IListener listener) {
        listeners.add(listener);
    }

    public void removeListener(IListener listener) {
        listeners.remove(listener);
    }

    public void save(Policy policy) {
        super.save(policy);
        onSaveOrUpdate(policy);
    }

    public Policy update(Policy policy) {
        Policy updatedPolicy = super.update(policy);
        onSaveOrUpdate(updatedPolicy);
        return updatedPolicy;
    }

    public void onSaveOrUpdate(Object object) {
        for(IListener listener : listeners) {
            listener.onSaveOrUpdate(((Policy)object).getCatalog().getSmartHome().getSmartHomeId(), object);
        }
    }
}
