package com.netcracker.smarthome.web.policy;

import com.netcracker.smarthome.model.enums.PolicyStatus;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

@FacesConverter("policyStatusConverter")
public class PolicyStatusConverter extends EnumConverter {
    public PolicyStatusConverter() {
        super(PolicyStatus.class);
    }
}
