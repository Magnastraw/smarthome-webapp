package com.netcracker.smarthome.business.services;

import com.netcracker.smarthome.dal.repositories.PolicyRepository;
import com.netcracker.smarthome.model.entities.Catalog;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.SmartObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PolicyService {
    private final PolicyRepository policyRepository;

    @Autowired
    public PolicyService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    @Transactional
    public void deletePolicy(long policyId) {
        policyRepository.delete(policyId);
    }

    @Transactional
    public Policy getPolicyById(long policyId) {
        return policyRepository.get(policyId);
    }

    @Transactional
    public void createPolicy(Policy policy) {
        policyRepository.save(policy);
    }

    @Transactional
    public Policy savePolicy(Policy policy) {
        return policyRepository.update(policy);
    }

    @Transactional
    public List<SmartObject> getObjectsWithActivePolicies() {
        List<SmartObject> objects = policyRepository.getObjectsWithActivePolicies();
        Set<SmartObject> inlineObjects = new HashSet<SmartObject>(policyRepository.getActivePoliciesInlineObjects());
        for (SmartObject object : inlineObjects
                ) {
            if (objects.contains(object))
                objects.get(objects.indexOf(object)).getAssignedPolicies().addAll(policyRepository.getActivePoliciesByInlineObject(object));
            else {
                object.setAssignedPolicies(new HashSet<Policy>(policyRepository.getActivePoliciesByInlineObject(object)));
                objects.add(object);
            }
        }
        return objects;
    }

    @Transactional
    public List<Policy> getPoliciesByCatalog(Catalog catalog) {
        return policyRepository.getPoliciesByCatalog(catalog);
    }
}
