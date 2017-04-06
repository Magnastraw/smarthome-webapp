package com.netcracker.smarthome.business.policy;

import com.netcracker.smarthome.business.policy.services.PolicyService;
import com.netcracker.smarthome.model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class ActivePoliciesHolder {
    private PolicyService policyService;
    private Map<SmartObject, Set<Policy>> activePolicies;

    @Autowired
    public ActivePoliciesHolder(PolicyService policyService) {
        this.policyService = policyService;
        activePolicies = new HashMap<SmartObject, Set<Policy>>();
    }

    @PostConstruct
    public void initialize() {
        List<SmartObject> objects = policyService.getObjectsWithActivePolicies();
        for (SmartObject object: objects
             ) {
            activePolicies.put(object, new HashSet<Policy>(object.getAssignedPolicies()));
        }
    }

    public void activatePolicy(Policy policy) {
        List<SmartObject> objects = policyService.getObjectsByPolicy(policy);
        for (SmartObject smartObject: objects
             ) {
            if (activePolicies.containsKey(smartObject)) {
                activePolicies.get(smartObject).add(policy);
            } else {
                Set<Policy> policies = new HashSet<Policy>();
                policies.add(policy);
                activePolicies.put(smartObject, policies);
            }
        }
    }

    public void deactivatePolicy(Policy policy) {
        List<SmartObject> objects = policyService.getObjectsByPolicy(policy);
        for (SmartObject smartObject: objects
                ) {
            Set<Policy> policies = activePolicies.get(smartObject);
            policies.remove(policy);
            if (policies.isEmpty()) {
                activePolicies.remove(smartObject);
            }
        }
    }

    public void assignPolicyToObject(SmartObject smartObject, Policy policyToAssign) {
        if (activePolicies.containsKey(smartObject))
            activePolicies.get(smartObject).add(policyToAssign);
    }

    public void removePolicyFromObject(SmartObject smartObject, Policy policyToRemove) {
        if (activePolicies.containsKey(smartObject))
            activePolicies.get(smartObject).remove(policyToRemove);
    }

//    public void init() {
//        List<Policy> policies = policyService.getActivePolicies();
//        for (Policy policy: policies) {
//            List<SmartObject> objects = policyService.getObjectsByPolicy(policy);
//            for (SmartObject smartObject: objects) {
//                tryAddObjectInMap(smartObject);
//            }
//        }
//    }
//
//    public boolean tryAddObjectInMap(SmartObject smartObject) {
//        if (activePolicies.containsKey(smartObject)) {
//            return false;
//        }
//        else {
//            List<Policy> policies = policyService.getActivePoliciesByObject(smartObject);
//            activePolicies.put(smartObject, new HashSet<Policy>(policies));
//            return true;
//        }
//    }
}
