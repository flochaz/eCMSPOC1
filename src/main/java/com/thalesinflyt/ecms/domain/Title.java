package com.thalesinflyt.ecms.domain;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Title.
 */

@Document(collection = "title")
public class Title implements Serializable {

    @Id
    private String id;

    @Field("name")
    private String name;
    
    @Field("exhibition_start_date")
    private LocalDate exhibitionStartDate;
    
    @Field("exhibition_end_date")
    private LocalDate exhibitionEndDate;
    
    @Field("creation_date")
    private LocalDate creationDate;
    
    @Field("latest_modified_date")
    private LocalDate latestModifiedDate;
    
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

    public LocalDate getExhibitionStartDate() {
        return exhibitionStartDate;
    }
    
    public void setExhibitionStartDate(LocalDate exhibitionStartDate) {
        this.exhibitionStartDate = exhibitionStartDate;
    }

    public LocalDate getExhibitionEndDate() {
        return exhibitionEndDate;
    }
    
    public void setExhibitionEndDate(LocalDate exhibitionEndDate) {
        this.exhibitionEndDate = exhibitionEndDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
    
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getLatestModifiedDate() {
        return latestModifiedDate;
    }
    
    public void setLatestModifiedDate(LocalDate latestModifiedDate) {
        this.latestModifiedDate = latestModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Title title = (Title) o;
        if(title.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, title.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Title{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", exhibitionStartDate='" + exhibitionStartDate + "'" +
            ", exhibitionEndDate='" + exhibitionEndDate + "'" +
            ", creationDate='" + creationDate + "'" +
            ", latestModifiedDate='" + latestModifiedDate + "'" +
            '}';
    }
}
