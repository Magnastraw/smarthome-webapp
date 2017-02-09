package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "home_params", schema = "public", catalog = "smarthome_db")
public class HomeParam implements Serializable {
    private long paramId;
    private String name;
    private String value;
    private SmartHome smartHome;
    private DataType dataType;

    public HomeParam() {
    }

    public HomeParam(String name, String value, SmartHome smartHome, DataType dataType) {
        this.name = name;
        this.value = value;
        this.smartHome = smartHome;
        this.dataType = dataType;
    }

    @Id
    @Column(name = "param_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "h_param_seq")
    @SequenceGenerator(name = "h_param_seq", sequenceName = "home_params_param_id_seq", allocationSize = 1)
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
    @JoinColumn(name = "smart_home_id", referencedColumnName = "smart_home_id", nullable = false)
    public SmartHome getSmartHome() {
        return smartHome;
    }

    public void setSmartHome(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "type_id", nullable = false)
    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof HomeParam)) return false;

        HomeParam homeParam = (HomeParam) o;

        return new EqualsBuilder()
                .append(getParamId(), homeParam.getParamId())
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
                .append("smartHome", getSmartHome())
                .append("dataType", getDataType())
                .toString();
    }
}
