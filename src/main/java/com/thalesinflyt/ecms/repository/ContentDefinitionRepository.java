package com.thalesinflyt.ecms.repository;

import com.thalesinflyt.ecms.domain.ContentDefinition;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ContentDefinition entity.
 */
public interface ContentDefinitionRepository extends MongoRepository<ContentDefinition,String> {

}
