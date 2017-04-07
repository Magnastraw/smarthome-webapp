package com.netcracker.smarthome.business.endpoints.controllers;

import com.netcracker.smarthome.business.HomeService;
import com.netcracker.smarthome.model.entities.SmartHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PoliciesController {

    @Autowired
    private HomeService homeService;

    @RequestMapping(value = "/policies",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity getPolicies(@RequestParam(value="houseId", required=true) long houseId) {
        SmartHome home = homeService.getHomeById(houseId);
        if (home == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        /* */
        return new ResponseEntity(HttpStatus.OK);
    }
}
