package com.netcracker.smarthome.model.entities;

import com.netcracker.smarthome.model.keys.AssignmentPK;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "assignments", schema = "public", catalog = "smarthome_db")
@IdClass(AssignmentPK.class)
public class Assignment implements Serializable {
    private long policyId;
    private long objectId;
    private String name;
    private Catalog catalog;
    private Policy policy;
    private SmartObject object;

    public Assignment() {
    }

    public Assignment(long policyId, long objectId, Catalog catalog) {
        this.policyId = policyId;
        this.objectId = objectId;
        this.catalog = catalog;
    }

    public Assignment(long policyId, long objectId, Catalog catalog, String name) {
        this.policyId = policyId;
        this.objectId = objectId;
        this.catalog = catalog;
        this.name = name;
    }

    @Id
    @Column(name = "policy_id", nullable = false)
    public long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(long policyId) {
        this.policyId = policyId;
    }

    @Id
    @Column(name = "object_id", nullable = false)
    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    @Basic
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "catalog_id", referencedColumnName = "catalog_id", nullable = false)
    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    @ManyToOne
    @JoinColumn(name = "policy_id", referencedColumnName = "policy_id", nullable = false, insertable = false, updatable = false)
    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    @ManyToOne
    @JoinColumn(name = "object_id", referencedColumnName = "smart_object_id", nullable = false, insertable = false, updatable = false)
    public SmartObject getObject() {
        return object;
    }

    public void setObject(SmartObject object) {
        this.object = object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Assignment)) return false;

        Assignment that = (Assignment) o;

        return new EqualsBuilder()
                .append(getPolicyId(), that.getPolicyId())
                .append(getObjectId(), that.getObjectId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getPolicyId())
                .append(getObjectId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("name", getName())
                .append("policy", getPolicy())
                .append("object", getObject())
                .append("catalog", getCatalog())
                .toString();
    }
}
