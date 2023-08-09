package com.mocksoftwarelicensingproject.licenseservice.clients;

import com.mocksoftwarelicensingproject.licenseservice.FallbackResponses.OrganizationFallback;
import com.mocksoftwarelicensingproject.licenseservice.model.Organization;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class OrganizationRestTemplateClient {

    private final KeycloakRestTemplate keycloakRestTemplate;

    public OrganizationRestTemplateClient(KeycloakRestTemplate keycloakRestTemplate) {
        this.keycloakRestTemplate = keycloakRestTemplate;
    }

    public Organization returnFallbackOrganization(){
        return OrganizationFallback.FALL_BACK_ORGANIZATION;
    }

    @CircuitBreaker(name = "organization-service-query", fallbackMethod = "returnFallbackOrganization")
    @Bulkhead(name = "organization-service-query-bulkhead", fallbackMethod = "returnFallbackOrganization")
    @Retry(name = "organization-service-query-retry", fallbackMethod = "returnFallbackOrganization")
    public Organization getOrganization(String organizationName){
        try {
            ResponseEntity<Organization> restExchange =
                    keycloakRestTemplate.exchange(
                            "http://localhost:8071/organization/v1/organization/{organizationName}",
                            HttpMethod.GET,
                            null,
                            Organization.class,
                            organizationName
                    );
            return restExchange.getBody();
        }catch (Exception e){
            return OrganizationFallback.FALL_BACK_ORGANIZATION;
        }
    }
}
