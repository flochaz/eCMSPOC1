package com.thalesinflyt.ecms.repository;

import com.thalesinflyt.ecms.domain.Title;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Title entity.
 */
public interface TitleRepository extends MongoRepository<Title,String> {

}
