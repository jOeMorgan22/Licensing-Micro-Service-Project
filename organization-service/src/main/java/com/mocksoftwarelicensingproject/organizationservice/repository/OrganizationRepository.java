package com.mocksoftwarelicensingproject.organizationservice.repository;

import com.mocksoftwarelicensingproject.organizationservice.model.Organization;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public interface OrganizationRepository extends CrudRepository<Organization, Long> {
    Optional<Organization> findOrganizationById(Long id);
    Optional<Organization> findOrganizationByOrganizationName(String organizationName);
    Optional<Organization> findOrganizationByEmail(String email);
    @Transactional
    void deleteOrganizationById(Long id);
}
