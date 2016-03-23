package com.thalesinflyt.ecms.domain;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.thalesinflyt.ecms.domain.enumeration.CatalogStatus;

/**
 * A Catalog.
 */

@Document(collection = "catalog")
public class Catalog implements Serializable {

    @Id
    private String id;

    @Field("name")
    private String name;
    
    @Field("status")
    private CatalogStatus status;
    
    @Field("last_modified")
    private LocalDate lastModified;
    
    @Field("start_date")
    private LocalDate startDate;
    
    @Field("end_date")
    private LocalDate endDate;
    
    @Field("catalog_shipment_deadline")
    private LocalDate catalogShipmentDeadline;
    
    @Field("description")
    private String description;
    
    @DBRef
    private Set<Title> titles = new HashSet<>();

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

    public CatalogStatus getStatus() {
        return status;
    }
    
    public void setStatus(CatalogStatus status) {
        this.status = status;
    }

    public LocalDate getLastModified() {
        return lastModified;
    }
    
    public void setLastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getCatalogShipmentDeadline() {
        return catalogShipmentDeadline;
    }
    
    public void setCatalogShipmentDeadline(LocalDate catalogShipmentDeadline) {
        this.catalogShipmentDeadline = catalogShipmentDeadline;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Title> getTitles() {
		return titles;
	}

	public void setTitles(Set<Title> titles) {
		this.titles = titles;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Catalog catalog = (Catalog) o;
        if(catalog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, catalog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Catalog{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", status='" + status + "'" +
            ", lastModified='" + lastModified + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", catalogShipmentDeadline='" + catalogShipmentDeadline + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
