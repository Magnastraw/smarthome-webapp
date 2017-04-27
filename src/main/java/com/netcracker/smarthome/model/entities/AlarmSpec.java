package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "alarm_specs", schema = "public", catalog = "smarthome_db")
public class AlarmSpec extends Spec {
    private String objectType;
    private List<Alarm> alarms;

    public AlarmSpec() {
    }

    public AlarmSpec(Catalog catalog) {
        super(catalog);
    }

    public AlarmSpec(String specName, String objectType, Catalog catalog) {
        super(specName, catalog);
        this.objectType = objectType;
    }

    @Id
    @Column(name = "spec_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "a_spec_seq")
    @SequenceGenerator(name = "a_spec_seq", sequenceName = "alarm_specs_spec_id_seq", allocationSize = 1)
    @Override
    public long getSpecId() {
        return super.getSpecId();
    }

    @Basic
    @Column(name = "spec_name", nullable = false)
    @Override
    public String getSpecName() {
        return super.getSpecName();
    }

    @Basic
    @Column(name = "object_type", nullable = false)
    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    @ManyToOne
    @JoinColumn(name = "catalog_id", referencedColumnName = "catalog_id", nullable = false)
    @Override
    public Catalog getCatalog() {
        return super.getCatalog();
    }

    @OneToMany(mappedBy = "alarmSpec")
    public List<Alarm> getAlarms() {
        return alarms;
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof AlarmSpec)) return false;

        AlarmSpec alarmSpec = (AlarmSpec) o;

        return new EqualsBuilder()
                .append(getSpecId(), alarmSpec.getSpecId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getSpecId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("specId", getSpecId())
                .append("specName", getSpecName())
                .append("objectType", getObjectType())
                .append("catalog", getCatalog())
                .toString();
    }
}