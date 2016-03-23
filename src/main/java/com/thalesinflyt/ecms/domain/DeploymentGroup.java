package com.thalesinflyt.ecms.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DeploymentGroup.
 */

@Document(collection = "deployment_group")
public class DeploymentGroup implements Serializable {

    @Id
    private String id;

    @Field("name")
    private String name;
    
    @Field("packaging_type")
    private String packagingType;
    
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

    public String getPackagingType() {
        return packagingType;
    }
    
    public void setPackagingType(String packagingType) {
        this.packagingType = packagingType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeploymentGroup deploymentGroup = (DeploymentGroup) o;
        if(deploymentGroup.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, deploymentGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DeploymentGroup{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", packagingType='" + packagingType + "'" +
            '}';
    }
}
