package com.netcracker.smarthome.business.policy.services;

import com.netcracker.smarthome.dal.repositories.PolicyRepository;
import com.netcracker.smarthome.model.entities.*;
import com.netcracker.smarthome.model.enums.PolicyStatus;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PolicyService {
    private final PolicyRepository policyRepository;

    @Autowired
    public  PolicyService (PolicyRepository policyRepository) {
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
    public Policy updatePolicy(Policy policy) {
        return policyRepository.update(policy);
    }

    @Transactional
    public List<Policy> getActivePoliciesByObject(SmartObject object) {
        return policyRepository.getActivePoliciesByObject(object);
    }

    @Transactional
    public List<Policy> getActivePolicies() {
        return policyRepository.getPoliciesByStatus(PolicyStatus.ACTIVE);
    }

    @Transactional
    public List<SmartObject> getObjectsByPolicy(Policy policy) {
        return policyRepository.getObjectsByPolicy(policy);
    }

    @Transactional
     public List<SmartObject> getObjectsWithActivePolicies() {
        return policyRepository.getObjectsWithActivePolicies();
     }

     @Transactional
     public List<Policy> getPoliciesByCatalog(Catalog catalog) {
        return policyRepository.getPoliciesByCatalog(catalog);
     }
}
