package com.netcracker.smarthome.model.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "alarm_spec", schema = "public", catalog = "smarthome_db")
public class AlarmSpec {
    private long specId;
    private String specName;
    private String objectType;
    private Catalog catalog;
    private Collection<Alarm> alarms;

    public AlarmSpec(String specName, String objectType, Catalog catalog) {
        this.specName = specName;
        this.objectType = objectType;
        this.catalog = catalog;
    }

    public AlarmSpec() {
    }

    @Id
    @Column(name = "spec_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "spec_seq")
    @SequenceGenerator(name = "spec_seq", sequenceName = "alarm_spec_id_seq")
    public long getSpecId() {
        return specId;
    }

    public void setSpecId(long specId) {
        this.specId = specId;
    }

    @Basic
    @Column(name = "spec_name", nullable = false, length = -1)
    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    @Basic
    @Column(name = "object_type", nullable = false, length = -1)
    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlarmSpec alarmSpec = (AlarmSpec) o;

        if (specId != alarmSpec.specId) return false;
        if (specName != null ? !specName.equals(alarmSpec.specName) : alarmSpec.specName != null) return false;
        if (objectType != null ? !objectType.equals(alarmSpec.objectType) : alarmSpec.objectType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (specId ^ (specId >>> 32));
        result = 31 * result + (specName != null ? specName.hashCode() : 0);
        result = 31 * result + (objectType != null ? objectType.hashCode() : 0);
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

    @OneToMany(mappedBy = "alarmSpec")
    public Collection<Alarm> getAlarms() {
        return alarms;
    }

    public void setAlarms(Collection<Alarm> alarms) {
        this.alarms = alarms;
    }
}
