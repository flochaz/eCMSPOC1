package com.thalesinflyt.ecms.repository;

import com.thalesinflyt.ecms.domain.Airline;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Airline entity.
 */
public interface AirlineRepository extends MongoRepository<Airline,String> {

}
