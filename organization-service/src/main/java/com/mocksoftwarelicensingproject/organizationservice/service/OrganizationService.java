package com.mocksoftwarelicensingproject.organizationservice.service;

import com.mocksoftwarelicensingproject.organizationservice.exception.OrganizationEmailAlreadyExistsException;
import com.mocksoftwarelicensingproject.organizationservice.exception.OrganizationNameAlreadyExistsException;
import com.mocksoftwarelicensingproject.organizationservice.exception.OrganizationNotFoundException;
import com.mocksoftwarelicensingproject.organizationservice.model.Organization;
import com.mocksoftwarelicensingproject.organizationservice.repository.OrganizationRepository;
import com.mocksoftwarelicensingproject.organizationservice.requestobj.OrganizationRequestObj;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository){
        this.organizationRepository = organizationRepository;
    }

    public Organization getOrganizationByName(String organizationName){
        Optional<Organization> queriedOrganization = organizationRepository
                .findOrganizationByOrganizationName(organizationName);
        if(queriedOrganization.isEmpty()){
            throw new OrganizationNotFoundException(organizationName);
        }
        return queriedOrganization.get();
    }

    public Organization getOrganizationByEmail(String organizationEmail){
        Optional<Organization> queriedOrganization = organizationRepository
                .findOrganizationByEmail(organizationEmail);
        if(queriedOrganization.isEmpty()){
            throw new OrganizationNotFoundException();
        }
        return queriedOrganization.get();
    }

    public Organization createOrganization(OrganizationRequestObj requestObj){
        if(organizationRepository.findOrganizationByOrganizationName(
                requestObj.getOrganizationName())
                .isPresent()){
            throw new OrganizationNameAlreadyExistsException(requestObj.getOrganizationName());
        }
        if(organizationRepository.findOrganizationByEmail(
                requestObj.getEmail())
                .isPresent()){
            throw new OrganizationEmailAlreadyExistsException(requestObj.getEmail());
        }
            Organization newOrganization = new Organization(
                    requestObj.getOrganizationName(),
                    requestObj.getContactName(),
                    requestObj.getEmail(),
                    requestObj.getPhoneNumber()
            );
        return organizationRepository.save(newOrganization);
    }

    public Organization updateOrganization(Long id, OrganizationRequestObj requestObj){
        Optional<Organization> queriedOrganization = organizationRepository.findOrganizationById(id);
        if(queriedOrganization.isEmpty()){
            throw new OrganizationNotFoundException(id);
        }
        Organization organizationToUpdate = queriedOrganization.get();
        organizationToUpdate.setName(requestObj.getOrganizationName());
        organizationToUpdate.setEmail(requestObj.getEmail());
        organizationToUpdate.setContactName(requestObj.getContactName());
        organizationToUpdate.setPhoneNumber(requestObj.getPhoneNumber());
        return organizationRepository.save(organizationToUpdate);
    }

    @Transactional
    public void deleteOrganizationById(Long id) {
        Optional<Organization> queriedOrganization = organizationRepository
                .findOrganizationById(id);
        if(queriedOrganization.isEmpty()){
            throw new OrganizationNotFoundException(id);
        }
        organizationRepository.deleteOrganizationById(id);
    }



}


