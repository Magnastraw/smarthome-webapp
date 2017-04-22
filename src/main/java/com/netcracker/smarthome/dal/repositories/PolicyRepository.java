package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.business.endpoints.IListener;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.web.common.ContextUtils;
import org.reflections.Reflections;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class PolicyRepository extends EntityRepository<Policy>{
    //private PolicyListener listeners;
    private List<IListener> listeners = new ArrayList<IListener>();

    public PolicyRepository() {
        super(Policy.class);
        //this.listeners = new PolicyListener();
        //initListeners();
    }

    /*protected void initListeners() {
        Reflections reflections = new Reflections("com.netcracker.smarthome");
        Set<Class<? extends IListener>> subTypes = reflections.getSubTypesOf(IListener.class);
        for (Class cl : subTypes) {
            String[] name = cl.getName().split("(?<=\\.)");
            listeners.add((IListener) ContextUtils.getBean(name[name.length-1]));
        }
    }*/

    public void addPolicyListener(IListener listener) {
        //listeners.addListener(listener);
        listeners.add(listener);
    }

    public void removePolicyListener(IListener listener) {
        //listeners.removeListener(listener);
        listeners.remove(listener);
    }

    public void save(Policy policy) {
        super.save(policy);
        //listeners.onSaveOrUpdate(policy);
        onSaveOrUpdate(policy);
    }

    public Policy update(Policy policy) {
        Policy updatedPolicy = super.update(policy);
        //listeners.onSaveOrUpdate(updatedPolicy);
        onSaveOrUpdate(updatedPolicy);
        return updatedPolicy;
    }

    public void onSaveOrUpdate(Object object) {
        for(IListener listener : listeners) {
            listener.onSaveOrUpdate(object);
        }
    }

}
