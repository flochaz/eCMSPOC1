package com.thalesinflyt.ecms.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ContentDefinition.
 */

@Document(collection = "content_definition")
public class ContentDefinition implements Serializable {

    @Id
    private String id;

    @Field("name")
    private String name;
    
    @Field("version")
    private Integer version;
    
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

    public Integer getVersion() {
        return version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContentDefinition contentDefinition = (ContentDefinition) o;
        if(contentDefinition.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, contentDefinition.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ContentDefinition{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", version='" + version + "'" +
            '}';
    }
}
