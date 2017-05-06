package com.netcracker.smarthome.business.services;

import com.netcracker.smarthome.business.endpoints.IListener;
import com.netcracker.smarthome.business.endpoints.IListenerSupport;
import com.netcracker.smarthome.dal.repositories.PolicyRepository;
import com.netcracker.smarthome.model.entities.Catalog;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.model.enums.PolicyStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class PolicyService implements IListenerSupport<IListener> {
    private final PolicyRepository policyRepository;
    private List<IListener> listeners = new ArrayList<IListener>();

    @Autowired
    public PolicyService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    public void addListener(IListener listener) {
        listeners.add(listener);
    }

    public void removeListener(IListener listener) {
        listeners.remove(listener);
    }

    private void onSaveOrUpdate(Object object) {
        for (IListener listener : listeners) {
            listener.onSaveOrUpdate(object);
        }
    }

    @Transactional
    public Policy getPolicyById(long policyId) {
        return policyRepository.get(policyId);
    }

    @Transactional
    public void createPolicy(Policy policy) {
        policyRepository.save(policy);
        onSaveOrUpdate(policy);
    }

    @Transactional
    public void deletePolicy(long policyId) {
        policyRepository.delete(policyId);
        onSaveOrUpdate(policyId);
    }

    @Transactional
    public Policy savePolicy(Policy policy) {
        Policy policyToSave = policyRepository.update(policy);
        onSaveOrUpdate(policyToSave);
        return policyToSave;
    }

    @Transactional
    public List<SmartObject> getObjectsWithActivePolicies() {
        List<SmartObject> objects = policyRepository.getObjectsWithActivePolicies(),
                inlineObjects = policyRepository.getActiveInlineObjects();
        for (SmartObject object : inlineObjects)
            if (objects.contains(object))
                objects.get(objects.indexOf(object)).getAssignedPolicies().addAll(policyRepository.getActivePoliciesByInlineObject(object.getSmartObjectId()));
            else {
                object.setAssignedPolicies(new HashSet<>(policyRepository.getActivePoliciesByInlineObject(object.getSmartObjectId())));
                objects.add(object);
            }
        return objects;
    }

    @Transactional
    public List<Policy> getActivePoliciesByHome(long homeId) {
        List<Policy> assignedPolicies = policyRepository.getAssignedActivePoliciesByHome(homeId),
                inlinePolicies;
        inlinePolicies = assignedPolicies.isEmpty() ? policyRepository.getInlineActivePoliciesByHome(homeId) : policyRepository.getInlineActivePoliciesByHome(homeId, assignedPolicies);
        for (Policy policy : inlinePolicies)
            policy.getAssignedObjects().addAll(policyRepository.getInlineObjects(policy));
        assignedPolicies.addAll(inlinePolicies);
        return assignedPolicies;
    }

    @Transactional
    public List<Policy> getPoliciesByCatalog(Catalog catalog) {
        return policyRepository.getPoliciesByCatalog(catalog);
    }

    @Transactional(readOnly = true)
    public Policy getActiveInitializedPolicy(long policyId) {
        Policy policy = policyRepository.getInitializedPolicy(policyId);
        if (policy != null && policy.getStatus() == PolicyStatus.ACTIVE) {
            policy.getAssignedObjects().addAll(policyRepository.getInlineObjects(policy));
        }
        if (policy.getAssignedObjects().isEmpty())
            return null;
        return policy;
    }
}
