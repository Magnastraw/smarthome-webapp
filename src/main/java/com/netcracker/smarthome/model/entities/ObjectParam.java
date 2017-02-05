package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "object_params", schema = "public", catalog = "smarthome_db")
public class ObjectParam implements Serializable {
    private long paramId;
    private String name;
    private String value;
    private DataType dataType;
    private SmartObject object;

    public ObjectParam() {
    }

    public ObjectParam(String name, String value, DataType dataType, SmartObject object) {
        this.name = name;
        this.value = value;
        this.dataType = dataType;
        this.object = object;
    }

    @Id
    @Column(name = "param_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "o_param_seq")
    @SequenceGenerator(name = "o_param_seq", sequenceName = "object_params_param_id_seq", allocationSize = 1)
    public long getParamId() {
        return paramId;
    }

    public void setParamId(long paramId) {
        this.paramId = paramId;
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
    @Column(name = "value", nullable = false)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "type_id", nullable = false)
    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    @ManyToOne
    @JoinColumn(name = "smart_object_id", referencedColumnName = "smart_object_id", nullable = false)
    public SmartObject getObject() {
        return object;
    }

    public void setObject(SmartObject object) {
        this.object = object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ObjectParam)) return false;

        ObjectParam that = (ObjectParam) o;

        return new EqualsBuilder()
                .append(getParamId(), that.getParamId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getParamId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("paramId", getParamId())
                .append("name", getName())
                .append("value", getValue())
                .append("dataType", getDataType())
                .append("object", getObject())
                .toString();
    }
}
