package com.netcracker.smarthome.model.entities;

import com.netcracker.smarthome.model.enums.PolicyStatus;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "policies", schema = "public", catalog = "smarthome_db")
public class Policy implements Serializable {
    private long policyId;
    private String name;
    private PolicyStatus status;
    private String description;
    private Catalog catalog;
    private List<Rule> rule;

    public Policy() {
    }

    public Policy(String name, PolicyStatus status, String description, Catalog catalog) {
        this.name = name;
        this.status = status;
        this.description = description;
        this.catalog = catalog;
    }

    @Id
    @Column(name = "policy_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "policy_seq")
    @SequenceGenerator(name = "policy_seq", sequenceName = "policies_policy_id_seq", allocationSize = 1)
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
    public List<Rule> getRule() {
        return rule;
    }

    public void setRule(List<Rule> rule) {
        this.rule = rule;
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
