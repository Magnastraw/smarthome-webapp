package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "objects", schema = "public", catalog = "smarthome_db")
public class SmartObject implements Serializable {
    private long smartObjectId;
    private String name;
    private String description;
    private List<Alarm> objectAlarms;
    private List<Alarm> subobjectAlarms;
    private List<Event> objectEvents;
    private List<Event> subobjectEvents;
    private List<Metric> objectMetrics;
    private List<Metric> subobjectMetrics;
    private List<ObjectParam> objectParams;
    private ObjectType objectType;
    private SmartObject parentObject;
    private List<SmartObject> subobjects;
    private Catalog catalog;
    private SmartHome smartHome;
    private long externalKey;

    public SmartObject() {
    }

    public SmartObject(String name, String description, ObjectType objectType, SmartObject parentObject, Catalog catalog, SmartHome smartHome, long externalKey) {
        this.name = name;
        this.description = description;
        this.objectType = objectType;
        this.parentObject = parentObject;
        this.catalog = catalog;
        this.smartHome = smartHome;
        this.externalKey = externalKey;
    }

    @Id
    @Column(name = "smart_object_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "obj_seq")
    @SequenceGenerator(name = "obj_seq", sequenceName = "objects_object_id_seq", allocationSize = 1)
    public long getSmartObjectId() {
        return smartObjectId;
    }

    public void setSmartObjectId(long smartObjectId) {
        this.smartObjectId = smartObjectId;
    }

    @Basic
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = true)
    public String getDescription() {
        return description;
    }

    @Basic
    @Column(name = "external_key", nullable = true)
    public long getExternalKey() {
        return externalKey;
    }

    public void setExternalKey(long externalKey) {
        this.externalKey = externalKey;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "object", cascade = CascadeType.ALL)
    public List<Alarm> getObjectAlarms() {
        return objectAlarms;
    }

    public void setObjectAlarms(List<Alarm> objectAlarms) {
        this.objectAlarms = objectAlarms;
    }

    @OneToMany(mappedBy = "subobject", cascade = CascadeType.ALL)
    public List<Alarm> getSubobjectAlarms() {
        return subobjectAlarms;
    }

    public void setSubobjectAlarms(List<Alarm> subobjectAlarms) {
        this.subobjectAlarms = subobjectAlarms;
    }

    @OneToMany(mappedBy = "object", cascade = CascadeType.ALL)
    public List<Event> getObjectEvents() {
        return objectEvents;
    }

    public void setObjectEvents(List<Event> objectEvents) {
        this.objectEvents = objectEvents;
    }

    @OneToMany(mappedBy = "subobject", cascade = CascadeType.ALL)
    public List<Event> getSubobjectEvents() {
        return subobjectEvents;
    }

    public void setSubobjectEvents(List<Event> subobjectEvents) {
        this.subobjectEvents = subobjectEvents;
    }

    @OneToMany(mappedBy = "object", cascade = CascadeType.ALL)
    public List<Metric> getObjectMetrics() {
        return objectMetrics;
    }

    public void setObjectMetrics(List<Metric> objectMetrics) {
        this.objectMetrics = objectMetrics;
    }

    @OneToMany(mappedBy = "subobject", cascade = CascadeType.ALL)
    public List<Metric> getSubobjectMetrics() {
        return subobjectMetrics;
    }

    public void setSubobjectMetrics(List<Metric> subobjectMetrics) {
        this.subobjectMetrics = subobjectMetrics;
    }

    @OneToMany(mappedBy = "object", cascade = CascadeType.ALL)
    public List<ObjectParam> getObjectParams() {
        return objectParams;
    }

    public void setObjectParams(List<ObjectParam> objectParams) {
        this.objectParams = objectParams;
    }

    @ManyToOne
    @JoinColumn(name = "object_type_id", referencedColumnName = "object_type_id", nullable = false)
    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    @ManyToOne
    @JoinColumn(name = "parent_smart_object_id", referencedColumnName = "smart_object_id")
    public SmartObject getParentObject() {
        return parentObject;
    }

    public void setParentObject(SmartObject parentObject) {
        this.parentObject = parentObject;
    }

    @OneToMany(mappedBy = "parentObject", cascade = CascadeType.ALL)
    public List<SmartObject> getSubobjects() {
        return subobjects;
    }

    public void setSubobjects(List<SmartObject> subobjects) {
        this.subobjects = subobjects;
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
    @JoinColumn(name = "smart_home_id", referencedColumnName = "smart_home_id", nullable = false)
    public SmartHome getSmartHome() {
        return smartHome;
    }

    public void setSmartHome(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof SmartObject)) return false;

        SmartObject that = (SmartObject) o;

        return new EqualsBuilder()
                .append(getSmartObjectId(), that.getSmartObjectId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getSmartObjectId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("smartObjectId", getSmartObjectId())
                .append("name", getName())
                .append("description", getDescription())
                .append("objectType", getObjectType())
                .append("parentObject", getParentObject())
                .append("catalog", getCatalog())
                .append("smartHome", getSmartHome())
                .toString();
    }
}
