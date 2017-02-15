package com.netcracker.smarthome.model.entities;

import com.netcracker.smarthome.model.enums.Type;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "data_types", schema = "public", catalog = "smarthome_db")
public class DataType implements Serializable {
    private long typeId;
    private String name;
    private Type type;
    private List<HomeParam> homeParam;
    private List<ObjectParam> objectParam;

    public DataType() {
    }

    public DataType(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Id
    @Column(name = "type_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "d_type_seq")
    @SequenceGenerator(name = "d_type_seq", sequenceName = "data_types_type_id_seq", allocationSize = 1)
    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    @Basic
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @OneToMany(mappedBy = "dataType")
    public List<HomeParam> getHomeParam() {
        return homeParam;
    }

    public void setHomeParam(List<HomeParam> homeParam) {
        this.homeParam = homeParam;
    }

    @OneToMany(mappedBy = "dataType")
    public List<ObjectParam> getObjectParam() {
        return objectParam;
    }

    public void setObjectParam(List<ObjectParam> objectParam) {
        this.objectParam = objectParam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof DataType)) return false;

        DataType dataType = (DataType) o;

        return new EqualsBuilder()
                .append(getTypeId(), dataType.getTypeId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getTypeId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("typeId", getTypeId())
                .append("name", getName())
                .append("type", getType())
                .toString();
    }
}
