package com.thalesinflyt.ecms.repository;

import com.thalesinflyt.ecms.domain.Catalog;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Catalog entity.
 */
public interface CatalogRepository extends MongoRepository<Catalog,String> {

}
