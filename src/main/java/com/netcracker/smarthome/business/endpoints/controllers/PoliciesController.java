package com.netcracker.smarthome.business.endpoints.controllers;

import com.netcracker.smarthome.business.HomeService;
import com.netcracker.smarthome.business.endpoints.IListener;
import com.netcracker.smarthome.business.policy.services.PolicyService;
import com.netcracker.smarthome.business.specs.CatalogService;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.enums.PolicyStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PoliciesController  {
    private static final Logger LOG = LoggerFactory.getLogger(PoliciesController.class);

    @Autowired
    private HomeService homeService;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private PolicyService policyService;

    @RequestMapping(value = "/policies",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity getPolicies(@RequestParam(value="houseId", required=true) long houseId) {
        SmartHome home = homeService.getHomeById(houseId);
        if (home == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        /* */
        //policyService.addPolicyListener(this);
        //policyService.savePolicy(new Policy("policy", PolicyStatus.ACTIVE, "description", catalogService.getRootCatalog("policiesRootCatalog", houseId)));


        return new ResponseEntity(HttpStatus.OK);
    }

    public void onSaveOrUpdate(Object object) {
        LOG.info("Call 'onSaveOrUpdate'");
    }
}
