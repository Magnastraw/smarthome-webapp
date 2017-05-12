package com.netcracker.smarthome.business.policy.core;

import com.netcracker.smarthome.business.endpoints.IListener;
import com.netcracker.smarthome.business.policy.core.nodes.PolicyNode;
import com.netcracker.smarthome.business.policy.events.PolicyEvent;
import com.netcracker.smarthome.business.policy.transform.core.PolicyConverter;
import com.netcracker.smarthome.business.services.PolicyService;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.SmartObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Component
public class PolicyEngine implements IListener {
    private final PolicyService policyService;
    private Map<PolicyNode, Set<Long>> policies;
    private ReadWriteLock lock;

    @Autowired
    public PolicyEngine(PolicyService policyService) {
        this.policyService = policyService;
        lock = new ReentrantReadWriteLock();
    }

    @PostConstruct
    private void initialize() {
        PolicyConverter converter = new PolicyConverter();
        policies = new HashMap<>();
        PolicyNode policyNode;
        List<Policy> policyList = policyService.getActivePolicies();
        Set<Long> assignedObjects;
        for (Policy policy : policyList) {
            policyNode = converter.convert(policy);
            assignedObjects = policy.getAssignedObjects().stream().map(SmartObject::getSmartObjectId).collect(Collectors.toSet());
            policies.put(policyNode, assignedObjects);
        }
        policyService.addListener(this);
    }

    @Override
    public void onSaveOrUpdate(Object object) {
        if (object instanceof Policy)
            reinitialize((Policy) object);
        else if (object instanceof Long)
            reinitialize((Long) object);
    }

    private void reinitialize(long policyToRemove) {
        lock.writeLock().lock();
        try {
            PolicyNode oldNode = policies.keySet().stream().filter(node -> node.getIdentifier() == policyToRemove).findFirst().orElse(null);
            policies.remove(oldNode);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void reinitialize(Policy activePolicy) {
        PolicyConverter converter = new PolicyConverter();
        PolicyNode newNode = converter.convert(activePolicy);
        Set<Long> assignedObjects = activePolicy.getAssignedObjects().stream().map(SmartObject::getSmartObjectId).collect(Collectors.toSet());
        lock.writeLock().lock();
        try {
            PolicyNode oldNode = policies.keySet().stream().filter(node -> node.getIdentifier() == activePolicy.getPolicyId()).findFirst().orElse(null);
            policies.remove(oldNode);
            policies.put(newNode, assignedObjects);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void handleEvent(PolicyEvent event) {
        lock.readLock().lock();
        try {
            long objectId = event.getSubobject() == null ? event.getObject().getSmartObjectId() : event.getSubobject().getSmartObjectId();
            for (PolicyNode policy : policies.keySet())
                if (policies.get(policy).contains(objectId))
                    policy.handle(event);
        } finally {
            lock.readLock().unlock();
        }
    }
}
