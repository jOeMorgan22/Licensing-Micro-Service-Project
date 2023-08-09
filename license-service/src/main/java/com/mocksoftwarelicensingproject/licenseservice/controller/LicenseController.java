package com.mocksoftwarelicensingproject.licenseservice.controller;

import com.mocksoftwarelicensingproject.licenseservice.model.License;
import com.mocksoftwarelicensingproject.licenseservice.model.OrganizationWithLicenses;
import com.mocksoftwarelicensingproject.licenseservice.requestobjs.LicenseRequest;
import com.mocksoftwarelicensingproject.licenseservice.service.LicenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/organization")
public class LicenseController {

    private final LicenseService licenseService;

    public LicenseController(LicenseService licenseService){
        this.licenseService = licenseService;
    }

    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/{organizationName}/add-license", method = RequestMethod.POST)
    public ResponseEntity<License> addNewOrganizationLicense(@PathVariable String organizationName,
                                                             @Valid @RequestBody LicenseRequest licenseRequest){
        License newLicense = new License(
                licenseService.generateUniqueUUID(organizationName, licenseRequest),
                organizationName,
                licenseRequest.getProductName().replaceAll("\\s", "-").toLowerCase(),
                licenseRequest.getLicenseType().replaceAll("\\s", "-").toLowerCase(),
                licenseRequest.getDescription());
        return new ResponseEntity<>(licenseService.createNewLicense(newLicense), HttpStatus.CREATED);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @RequestMapping(value = "/{organizationName}/license", method = RequestMethod.GET)
    public ResponseEntity<List<License>> getOrganizationLicensesByOrganizationName(@PathVariable String organizationName){
        return new ResponseEntity<>(licenseService.findLicensesByOrganizationName(organizationName),
                HttpStatus.OK);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @RequestMapping(value = "/{organizationName}/license/{id}", method = RequestMethod.GET)
    public ResponseEntity<License> getLicenseById(@PathVariable String organizationName,
                                                  @PathVariable Long id){
        License queriedLicense = licenseService.findLicenseById(organizationName, id);
        return new ResponseEntity<>(queriedLicense, HttpStatus.OK);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @RequestMapping(value = "/{organizationName}/license/product-name/{productName}", method = RequestMethod.GET)
    public ResponseEntity<List<License>> getOrganizationLicensesByProductName(@PathVariable String organizationName,
                                                                  @PathVariable String productName){
        return new ResponseEntity<>(licenseService.findLicensesByProductName(productName, organizationName),
                HttpStatus.OK);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @RequestMapping(value = "/{organizationName}/license/license-type/{licenseType}", method = RequestMethod.GET)
    public ResponseEntity<List<License>> getOrganizationLicensesByLicenseType(@PathVariable String organizationName,
                                                                  @PathVariable String licenseType){
        return new ResponseEntity<>(licenseService.findLicensesByLicenseType(licenseType, organizationName),
                HttpStatus.OK);
    }

    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/{organizationName}/license/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> deleteLicenseById(@PathVariable String organizationName,
                                                        @PathVariable Long id){
        licenseService.deleteLicenseById(organizationName, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @RequestMapping(value = "/license-by-type/{licenseType}", method = RequestMethod.GET)
    public ResponseEntity<List<OrganizationWithLicenses>> getAllLicensesByType(@PathVariable String licenseType){
        return new ResponseEntity<>(licenseService.getAllLicensesByType(licenseType), HttpStatus.OK);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @RequestMapping(value = "/license-by-product-name/{productName}", method = RequestMethod.GET)
    public ResponseEntity<List<OrganizationWithLicenses>> getAllLicensesByProductName(@PathVariable String productName){
        return new ResponseEntity<>(licenseService.getAllLicensesByProductName(productName), HttpStatus.OK);
    }

}
