package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;


@Entity
@Table(name = "charts", schema = "public", catalog = "smarthome_db")
public class Chart {
    private long chartId;
    private String chartOption;
    private long col;
    private long row;
    private long sizeX;
    private long sizeY;
    private String requestOption;
    private BigDecimal refreshInterval;
    private Dashboard dashboard;

    public Chart() {
    }

    public Chart(String chartOption, String requestOption, BigDecimal refreshInterval, Dashboard dashboard) {
        this.chartOption = chartOption;
        this.requestOption = requestOption;
        this.refreshInterval = refreshInterval;
        this.dashboard = dashboard;
        this.col=1;
        this.row=1;
        this.sizeX=3;
        this.sizeY=4;
    }

    @Id
    @Column(name = "chart_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chart_seq")
    @SequenceGenerator(name = "chart_seq", sequenceName = "charts_chart_id_seq", allocationSize = 1)
    public long getChartId() {
        return chartId;
    }

    public void setChartId(long chartId) {
        this.chartId = chartId;
    }

    @Basic
    @Column(name = "chart_option")
    public String getChartOption() {
        return chartOption;
    }

    public void setChartOption(String chartOption) {
        this.chartOption = chartOption;
    }

    @Basic
    @Column(name = "col")
    public long getCol() {
        return col;
    }

    public void setCol(long col) {
        this.col = col;
    }

    @Basic
    @Column(name = "row")
    public long getRow() {
        return row;
    }

    public void setRow(long row) {
        this.row = row;
    }

    @Basic
    @Column(name = "size_x")
    public long getSizeX() {
        return sizeX;
    }

    public void setSizeX(long sizeX) {
        this.sizeX = sizeX;
    }

    @Basic
    @Column(name = "size_y")
    public long getSizeY() {
        return sizeY;
    }

    public void setSizeY(long sizeY) {
        this.sizeY = sizeY;
    }

    @Basic
    @Column(name = "request_option")
    public String getRequestOption() {
        return requestOption;
    }

    public void setRequestOption(String requestOption) {
        this.requestOption = requestOption;
    }

    @Basic
    @Column(name = "refresh_interval")
    public BigDecimal getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(BigDecimal refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    @ManyToOne
    @JoinColumn(name = "dashboard_id", referencedColumnName = "dashboard_id", nullable = false)
    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }
    @Override

    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Chart)) return false;

        Chart chart = (Chart) o;

        return new EqualsBuilder()
                .append(getChartId(), chart.getChartId())
                .isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getChartId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("chartId", getChartId())
                .append("chartOption", getChartOption())
                .append("requestOption", getRequestOption())
                .append("refreshInterval", getRefreshInterval())
                .append("dashBoard", getDashboard())
                .toString();
    }
}
