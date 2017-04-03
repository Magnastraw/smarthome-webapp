package com.netcracker.smarthome.business.policy;

import com.netcracker.smarthome.business.policy.services.PolicyService;
import com.netcracker.smarthome.model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

// TODO : переделать этот класс
@Component
public class ActivePolicyMap {
    private PolicyService policyService;
    public Map<SmartObject, List<Policy>> policyMap = new HashMap<SmartObject, List<Policy>>();

    @Autowired
    public ActivePolicyMap(PolicyService policyService) {
        this.policyService = policyService;
    }

    public void init() {
        List<Policy> policies = policyService.getActivePolicies();
        for (Policy policy: policies) {
            List<SmartObject> objects = policyService.getObjectsByPolicy(policy);
            for (SmartObject smartObject: objects) {
                tryAddObjectInMap(smartObject);
            }
        }
    }

    public void addPolicyInMap(Policy policy) {
        List<SmartObject> objects = policyService.getObjectsByPolicy(policy);
        for (SmartObject smartObject: objects
             ) {
            if (policyMap.containsKey(smartObject)) {
                policyMap.get(smartObject).add(policy);
            } else {
                List<Policy> policies = new ArrayList<Policy>();
                policies.add(policy);
                policyMap.put(smartObject, policies);
            }
        }
    }

    public boolean tryAddObjectInMap(SmartObject smartObject) {
        if (policyMap.containsKey(smartObject)) {
            return false;
        }
        else {
            List<Policy> policies = policyService.getActivePoliciesByObject(smartObject);
            policyMap.put(smartObject, policies);
            return true;
        }
    }

    public void removePolicy(Policy policy) {
        List<SmartObject> objects = policyService.getObjectsByPolicy(policy);
        for (SmartObject smartObject: objects
                ) {
            List<Policy> policies = policyMap.get(smartObject);
            policies.remove(policy);
            if (policies.size() == 0) {
                policyMap.remove(smartObject);
            }
        }
    }

    public void removeObject(SmartObject smartObject) {
        policyMap.remove(smartObject);
    }
}
