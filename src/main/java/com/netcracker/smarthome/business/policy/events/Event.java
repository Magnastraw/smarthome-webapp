package com.netcracker.smarthome.business.policy.events;

import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.model.entities.Spec;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.sql.Timestamp;

public abstract class Event implements Serializable {
    private EventType type;
    private SmartObject object;
    private SmartObject subobject;
    private Timestamp registryDate;
    private Spec spec;

    public Event() {
    }

    public Event(EventType type, SmartObject object, SmartObject subobject, Timestamp registryDate, Spec spec) {
        this.type = type;
        this.object = object;
        this.subobject = subobject;
        this.registryDate = registryDate;
        this.spec = spec;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public SmartObject getObject() {
        return object;
    }

    public void setObject(SmartObject object) {
        this.object = object;
    }

    public SmartObject getSubobject() {
        return subobject;
    }

    public void setSubobject(SmartObject subobject) {
        this.subobject = subobject;
    }

    public Timestamp getRegistryDate() {
        return registryDate;
    }

    public void setRegistryDate(Timestamp registryDate) {
        this.registryDate = registryDate;
    }

    public Spec getSpec() {
        return spec;
    }

    public void setSpec(Spec spec) {
        this.spec = spec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Event)) return false;

        Event event = (Event) o;

        return new EqualsBuilder()
                .append(getType(), event.getType())
                .append(getObject(), event.getObject())
                .append(getSubobject(), event.getSubobject())
                .append(getRegistryDate(), event.getRegistryDate())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getType())
                .append(getObject())
                .append(getSubobject())
                .append(getRegistryDate())
                .toHashCode();
    }
}
