package com.netcracker.smarthome.business.policy.transform.json;

import com.netcracker.smarthome.business.endpoints.transformators.ITransformator;
import com.netcracker.smarthome.business.policy.transform.json.entities.JsonPolicy;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.SmartObject;

import java.util.stream.Collectors;

public class PolicyTransformer implements ITransformator<Policy, JsonPolicy> {
    private final RuleTransformer ruleTransformer;

    public PolicyTransformer() {
        this.ruleTransformer = new RuleTransformer();
    }

    @Override
    public Policy fromJsonEntity(JsonPolicy jsonPolicy) {
        throw new UnsupportedOperationException("Not supported operation!");
    }

    @Override
    public JsonPolicy toJsonEntity(Policy policy) {
        JsonPolicy jsonPolicy = new JsonPolicy();
        jsonPolicy.setId(policy.getPolicyId());
        jsonPolicy.setName(policy.getName());
        jsonPolicy.setAssignedObjects(policy.getAssignedObjects()
                .stream()
                .map(SmartObject::getExternalKey)
                .collect(Collectors.toList())
        );
        jsonPolicy.setRules(policy.getRules()
                .stream()
                .map(ruleTransformer::toJsonEntity)
                .collect(Collectors.toList())
        );
        return jsonPolicy;
    }
}
