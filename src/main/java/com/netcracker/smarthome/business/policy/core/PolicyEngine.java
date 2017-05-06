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
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

//TODO: change reinitialization (and maybe storing model)
@Component
public class PolicyEngine implements IListener {
    private final PolicyService policyService;
    private Map<Long, Set<PolicyNode>> policiesByObjects;
    private ReadWriteLock lock;

    @Autowired
    public PolicyEngine(PolicyService policyService) {
        this.policyService = policyService;
        policiesByObjects = new HashMap<>();
        lock = new ReentrantReadWriteLock();
    }

    @PostConstruct
    private void initialize() {
        PolicyConverter converter = new PolicyConverter();
        List<SmartObject> objects = policyService.getObjectsWithActivePolicies();
        Map<Long, PolicyNode> initializedNodes = new HashMap<>();
        Set<PolicyNode> assignedPolicies;
        PolicyNode policyNode;
        for (SmartObject object : objects) {
            assignedPolicies = new HashSet<>();
            for (Policy policy : object.getAssignedPolicies())
                if (initializedNodes.containsKey(policy.getPolicyId()))
                    assignedPolicies.add(initializedNodes.get(policy.getPolicyId()));
                else {
                    policyNode = converter.convert(policy);
                    assignedPolicies.add(policyNode);
                    initializedNodes.put(policyNode.getIdentifier(), policyNode);
                }
            policiesByObjects.put(object.getSmartObjectId(), assignedPolicies);
        }
//        policyService.addListener(this);
    }

    private void reinitialize(long policyId) {
        lock.writeLock().lock();
        try {
            for (Set<PolicyNode> policyNodes : policiesByObjects.values())
                policyNodes.removeIf(node -> node.getIdentifier() == policyId);
            policiesByObjects = policiesByObjects.entrySet().stream()
                    .filter(entry -> !entry.getValue().isEmpty())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            Policy policy = policyService.getActiveInitializedPolicy(policyId);
            if (policy != null) {
                PolicyConverter converter = new PolicyConverter();
                PolicyNode policyNode = converter.convert(policy);
                for (SmartObject object : policy.getAssignedObjects()) {
                    if (!policiesByObjects.containsKey(object.getSmartObjectId()))
                        policiesByObjects.put(object.getSmartObjectId(), new HashSet<>());
                    policiesByObjects.get(object.getSmartObjectId()).add(policyNode);
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void onSaveOrUpdate(Object object) {
        reinitialize((Long) object);
    }

    public void handleEvent(PolicyEvent event) {
        lock.readLock().lock();
        try {
            long objectId = event.getSubobject() == null ? event.getObject().getSmartObjectId() : event.getSubobject().getSmartObjectId();
            for (PolicyNode policy : policiesByObjects.get(objectId))
                policy.handle(event);
        } finally {
            lock.readLock().unlock();
        }
    }
}
