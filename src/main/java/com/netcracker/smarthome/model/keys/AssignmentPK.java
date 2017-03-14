package com.netcracker.smarthome.model.keys;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class AssignmentPK implements Serializable {
    private long policyId;
    private long objectId;

    @Column(name = "policy_id", nullable = false)
    @Id
    public long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(long policyId) {
        this.policyId = policyId;
    }

    @Column(name = "object_id", nullable = false)
    @Id
    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof AssignmentPK)) return false;

        AssignmentPK that = (AssignmentPK) o;

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
}
