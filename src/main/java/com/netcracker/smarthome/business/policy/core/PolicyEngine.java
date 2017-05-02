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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PolicyEngine implements IListener {
    private final PolicyService policyService;
    private Map<Long, Set<PolicyNode>> policiesByObjects;

    @Autowired
    public PolicyEngine(PolicyService policyService) {
        this.policyService = policyService;
    }

//    @PostConstruct
    private void initialize() {
        PolicyConverter converter = new PolicyConverter();
        List<SmartObject> objects = policyService.getObjectsWithActivePolicies();
        for (SmartObject object : objects)
            policiesByObjects.put(
                    object.getSmartObjectId(),
                    object.getAssignedPolicies().stream()
                            .map(converter::convert)
                            .collect(Collectors.toSet())
            );
        policyService.addListener(this);
    }

    private void reinitialize(long policyId) {
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
    }

    @Override
    public void onSaveOrUpdate(Object object) {
        reinitialize((Long) object);
    }

    public void handleEvent(PolicyEvent event) {
        long objectId = event.getSubobject() == null ? event.getObject().getSmartObjectId() : event.getSubobject().getSmartObjectId();
        for (PolicyNode policy : policiesByObjects.get(objectId))
            policy.handle(event);
    }
}
