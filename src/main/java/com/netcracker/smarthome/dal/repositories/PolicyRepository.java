package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.business.endpoints.IListener;
import com.netcracker.smarthome.business.endpoints.ListenersInitializer;
import com.netcracker.smarthome.model.entities.Policy;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public class PolicyRepository extends EntityRepository<Policy> {
    //private Set<IListener> listeners;

    public PolicyRepository() {
        super(Policy.class);
        //listeners = ListenersInitializer.init();
    }

    /*public void addListener(IListener listener) {
        listeners.add(listener);
    }

    public void removeListener(IListener listener) {
        listeners.remove(listener);
    }

    public void savePolicy(Policy policy) {
        super.savePolicy(policy);
        onSaveOrUpdate(policy);
    }

    public Policy updatePolicy(Policy policy) {
        Policy updatedPolicy = super.updatePolicy(policy);
        onSaveOrUpdate(updatedPolicy);
        return updatedPolicy;
    }

    public void onSaveOrUpdate(Object object) {
        for(IListener listener : listeners) {
            listener.onSaveOrUpdate(((Policy)object).getCatalog().getSmartHome().getSmartHomeId(), object);
        }
    }*/
}
