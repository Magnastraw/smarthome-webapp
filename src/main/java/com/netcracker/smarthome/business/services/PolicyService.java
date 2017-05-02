package com.netcracker.smarthome.business.services;

import com.netcracker.smarthome.ApplicationContextHolder;
import com.netcracker.smarthome.business.endpoints.IListener;
import com.netcracker.smarthome.dal.repositories.PolicyRepository;
import com.netcracker.smarthome.model.entities.Policy;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class PolicyService {
    private Set<IListener> listeners;
    private PolicyRepository policyRepository;

    @Autowired
    public PolicyService (PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
        initListeners();
    }

    private void initListeners() {
        listeners = new HashSet<IListener>();
        Reflections reflections = new Reflections("com.netcracker.smarthome");
        Set<Class<? extends IListener>> subTypes = reflections.getSubTypesOf(IListener.class);
        for (Class cl : subTypes) {
            String[] name = cl.getName().split("(?<=\\.)");
            String beanName = name[name.length-1].substring(0,1).toLowerCase()+name[name.length-1].substring(1);
            try {
                listeners.add((IListener) ApplicationContextHolder.getApplicationContext().getBean(beanName));
            } catch (Exception ex) {}
        }
    }

    public void addListener(IListener listener) {
        listeners.add(listener);
    }

    public void removeListener(IListener listener) {
        listeners.remove(listener);
    }

    public void onSaveOrUpdate(Object object) {
        for(IListener listener : listeners) {
            listener.onSaveOrUpdate(((Policy)object).getCatalog().getSmartHome().getSmartHomeId(), object);
        }
    }


    @Transactional
    public void save(Policy policy) {
        policyRepository.save(policy);
        onSaveOrUpdate(policy);
    }

    public Policy update(Policy policy) {
        Policy updatedPolicy = policyRepository.update(policy);
        onSaveOrUpdate(updatedPolicy);
        return updatedPolicy;
    }

}
