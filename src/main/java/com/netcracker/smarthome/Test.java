package com.netcracker.smarthome;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.smarthome.business.policy.transform.json.PolicyTransformer;
import com.netcracker.smarthome.business.policy.transform.json.entities.JsonPolicy;
import com.netcracker.smarthome.business.services.PolicyService;
import com.netcracker.smarthome.dal.repositories.PolicyRepository;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.SmartObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        PolicyService service = context.getBean(PolicyService.class);
        PolicyRepository repository = context.getBean(PolicyRepository.class);
        PolicyTransformer transformer = new PolicyTransformer();
        List<JsonPolicy> jsonPolicies = new ArrayList<>();
        for (Policy policy : repository.getActivePoliciesByInlineObject(2L)
                ) {
            jsonPolicies.add(transformer.toJsonEntity(policy));
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("policies.json"), jsonPolicies);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<SmartObject> objects = service.getObjectsWithActivePolicies();
        System.out.println();
    }
}
