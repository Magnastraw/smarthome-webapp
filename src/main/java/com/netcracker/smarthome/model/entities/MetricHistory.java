package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "metrics_history", schema = "public", catalog = "smarthome_db")
public class MetricHistory implements Serializable {
    private long historyId;
    private Timestamp readDate;
    private BigDecimal value;
    private Metric metric;

    public MetricHistory() {
    }

    public MetricHistory(Timestamp readDate, BigDecimal value, Metric metric) {
        this.readDate = readDate;
        this.value = value;
        this.metric = metric;
    }

    @Id
    @Column(name = "history_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_history_seq")
    @SequenceGenerator(name = "m_history_seq", sequenceName = "metrics_history_id_seq", allocationSize = 1)
    public long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(long historyId) {
        this.historyId = historyId;
    }

    @Basic
    @Column(name = "read_date", nullable = false)
    public Timestamp getReadDate() {
        return readDate;
    }

    public void setReadDate(Timestamp readDate) {
        this.readDate = readDate;
    }

    @Basic
    @Column(name = "value", nullable = false, precision = 3)
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @ManyToOne
    @JoinColumn(name = "metric_id", referencedColumnName = "metric_id", nullable = false)
    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof MetricHistory)) return false;

        MetricHistory that = (MetricHistory) o;

        return new EqualsBuilder()
                .append(getHistoryId(), that.getHistoryId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getHistoryId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("historyId", getHistoryId())
                .append("readDate", getReadDate())
                .append("value", getValue())
                .append("metric", getMetric())
                .toString();
    }
}
