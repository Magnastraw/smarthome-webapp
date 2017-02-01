package com.netcracker.smarthome.model.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "policies", schema = "public", catalog = "smarthome_db")
public class Policy implements Serializable {
    private long policyId;
    private String name;
    private String description;
    private Catalog catalog;
    private Collection<Rule> rules;

    public Policy() {
    }

    public Policy(String name, String description, Catalog catalog) {
        this.name = name;
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
    @Column(name = "name", nullable = false, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Policy policy = (Policy) o;

        if (policyId != policy.policyId) return false;
        if (name != null ? !name.equals(policy.name) : policy.name != null) return false;
        if (description != null ? !description.equals(policy.description) : policy.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (policyId ^ (policyId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "catalog_id", referencedColumnName = "catalog_id", nullable = false)
    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    @OneToMany(mappedBy = "policy")
    public Collection<Rule> getRules() {
        return rules;
    }

    public void setRules(Collection<Rule> rules) {
        this.rules = rules;
    }
}
