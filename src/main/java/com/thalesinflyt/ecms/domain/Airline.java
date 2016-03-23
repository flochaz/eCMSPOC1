package com.thalesinflyt.ecms.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Airline.
 */

@Document(collection = "airline")
public class Airline implements Serializable {

    @Id
    private String id;

    @Field("name")
    private String name;
    
    @Field("icao_code")
    private String icaoCode;
    
    @DBRef
    private Set<Catalog> catalogs = new HashSet<>();

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getIcaoCode() {
        return icaoCode;
    }
    
    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    public Set<Catalog> getCatalogs() {
		return catalogs;
	}

	public void setCatalogs(Set<Catalog> catalogs) {
		this.catalogs = catalogs;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Airline airline = (Airline) o;
        if(airline.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, airline.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Airline{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", icaoCode='" + icaoCode + "'" +
            '}';
    }
}
