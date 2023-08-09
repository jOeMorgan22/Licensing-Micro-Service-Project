package com.mocksoftwarelicensingproject.organizationservice.controller;

import com.mocksoftwarelicensingproject.organizationservice.model.Organization;
import com.mocksoftwarelicensingproject.organizationservice.requestobj.OrganizationRequestObj;
import com.mocksoftwarelicensingproject.organizationservice.service.OrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.UUID;

@RestController
@RequestMapping("v1/organization")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService){
        this.organizationService = organizationService;
    }

    @RolesAllowed({"ADMIN", "USER"})
    @RequestMapping(value = "/{organizationName}", method = RequestMethod.GET)
    public ResponseEntity<Organization> getOrganizationByName(
            @PathVariable String organizationName){
        Organization queriedOrganization = organizationService.getOrganizationByName(organizationName);
        return new ResponseEntity<>(queriedOrganization, HttpStatus.OK);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Organization> createNewOrganization(
            @RequestBody OrganizationRequestObj requestObj){
        requestObj.setOrganizationName(requestObj
                .getOrganizationName()
                .replaceAll("\\s", "-")
                .toLowerCase());
        Organization createdOrganization = organizationService.createOrganization(requestObj);
        return new ResponseEntity<>(createdOrganization, HttpStatus.CREATED);
    }

    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/update/{organizationId}", method = RequestMethod.POST)
    public ResponseEntity<Organization> updateExistingOrganization(@PathVariable Long organizationId,
                                                                   @RequestBody OrganizationRequestObj requestObj){
        requestObj.setOrganizationName(requestObj
                .getOrganizationName()
                .replaceAll("\\s", "-")
                .toLowerCase());
        Organization updatedOrganization = organizationService
                .updateOrganization(organizationId,requestObj);
        return new ResponseEntity<>(updatedOrganization, HttpStatus.OK);
    }



    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/delete/{organizationId}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> deleteOrganizationByOrganizationName(@PathVariable Long organizationId){
        organizationService.deleteOrganizationById(organizationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
