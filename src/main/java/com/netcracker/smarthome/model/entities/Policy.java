package com.netcracker.smarthome.model.entities;

import com.netcracker.smarthome.model.enums.PolicyStatus;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "policies", schema = "public", catalog = "smarthome_db")
public class Policy implements Serializable {
    private long policyId;
    private String name;
    private PolicyStatus status;
    private String description;
    private Catalog catalog;
    private Set<Rule> rules;
    private Set<SmartObject> assignedObjects;

    public Policy() {
    }

    public Policy(String name, PolicyStatus status, Catalog catalog, String description) {
        this.name = name;
        this.status = status;
        this.description = description;
        this.catalog = catalog;
    }

    @Id
    @Column(name = "policy_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "policy_seq")
    @SequenceGenerator(name = "policy_seq", sequenceName = "policies_policy_seq", allocationSize = 1)
    public long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(long policyId) {
        this.policyId = policyId;
    }

    @Basic
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    public PolicyStatus getStatus() {
        return status;
    }

    public void setStatus(PolicyStatus status) {
        this.status = status;
    }

    @Basic
    @Column(name = "description", nullable = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne
    @JoinColumn(name = "catalog_id", referencedColumnName = "catalog_id", nullable = false)
    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL)
    @OrderBy(value = "ruleId asc")
    public Set<Rule> getRules() {
        return rules;
    }

    public void setRules(Set<Rule> rules) {
        this.rules = rules;
    }

    @ManyToMany
    @JoinTable(name = "assignments", joinColumns = {
            @JoinColumn(name = "policy_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "object_id",
                    nullable = false, updatable = false)})
    public Set<SmartObject> getAssignedObjects() {
        return assignedObjects;
    }

    public void setAssignedObjects(Set<SmartObject> assignedObjects) {
        this.assignedObjects = assignedObjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Policy)) return false;

        Policy policy = (Policy) o;

        return new EqualsBuilder()
                .append(getPolicyId(), policy.getPolicyId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getPolicyId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("policyId", getPolicyId())
                .append("name", getName())
                .append("status", getStatus())
                .append("description", getDescription())
                .append("catalog", getCatalog())
                .toString();
    }
}
