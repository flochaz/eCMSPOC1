package com.thalesinflyt.ecms.repository;

import com.thalesinflyt.ecms.domain.DeploymentGroup;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the DeploymentGroup entity.
 */
public interface DeploymentGroupRepository extends MongoRepository<DeploymentGroup,String> {

}
