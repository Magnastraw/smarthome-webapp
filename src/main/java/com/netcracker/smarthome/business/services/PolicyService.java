package com.netcracker.smarthome.business.services;

import com.netcracker.smarthome.business.endpoints.IListener;
import com.netcracker.smarthome.dal.repositories.PolicyRepository;
import com.netcracker.smarthome.model.entities.Policy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PolicyService {
    @Autowired
    private PolicyRepository policyRepository;

    @Transactional
    public void savePolicy(Policy policy) {
        policyRepository.save(policy);
    }

    public void addPolicyListener(IListener listener) {
        policyRepository.addListener(listener);
    }
}
