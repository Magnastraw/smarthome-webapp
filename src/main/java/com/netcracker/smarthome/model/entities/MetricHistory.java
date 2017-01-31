package com.netcracker.smarthome.model.entities;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "metrics_history", schema = "public", catalog = "smarthome_db")
public class MetricHistory {
    private long historyId;
    private Timestamp readDate;
    private BigInteger value;
    private Metric metric;

    public MetricHistory() {
    }

    public MetricHistory(Timestamp readDate, BigInteger value, Metric metric) {
        this.readDate = readDate;
        this.value = value;
        this.metric = metric;
    }

    @Id
    @Column(name = "history_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_history_seq")
    @SequenceGenerator(name = "m_history_seq", sequenceName = "metrics_history_id_seq")
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
    @Column(name = "value", nullable = false, precision = 0)
    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetricHistory that = (MetricHistory) o;

        if (historyId != that.historyId) return false;
        if (readDate != null ? !readDate.equals(that.readDate) : that.readDate != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (historyId ^ (historyId >>> 32));
        result = 31 * result + (readDate != null ? readDate.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "metric_id", referencedColumnName = "metric_id", nullable = false)
    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }
}
