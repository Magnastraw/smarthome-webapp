package com.netcracker.smarthome.business.policy.exec;

import com.netcracker.smarthome.business.policy.events.PolicyEvent;
import com.netcracker.smarthome.business.policy.exec.nodes.PolicyNode;
import com.netcracker.smarthome.business.services.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Set;

@Component
public class PolicyEngine {
    private final PolicyService policyService;
    private HashMap<Long, Set<PolicyNode>> policiesByObjects;

    @Autowired
    public PolicyEngine(PolicyService policyService) {
        this.policyService = policyService;
    }

    @PostConstruct
    private void initialize() {
        //TODO: add engine initialization
    }

    public void handleEvent(PolicyEvent event) {
        for (PolicyNode policy : policiesByObjects.get(event.getSubobject().getSmartObjectId())
                ) {
            policy.handle(event);
        }
    }
}
