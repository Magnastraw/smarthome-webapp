package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "charts", schema = "public", catalog = "smarthome_db")
public class Chart {
    private long chartId;
    private String chartOption;
    private String requestOption;
    private BigDecimal refreshInterval;
    private Dashboard dashboard;

    public Chart(){

    }

    public Chart(String chartOption, String requestOption, BigDecimal refreshInterval, Dashboard dashboard) {
        this.chartOption = chartOption;
        this.requestOption = requestOption;
        this.refreshInterval = refreshInterval;
        this.dashboard = dashboard;
    }

    @Id
    @Column(name = "chart_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chart_seq")
    @SequenceGenerator(name = "chart_seq", sequenceName = "charts_chart_id_seq", allocationSize = 1)
    public long getChartId() {
        return chartId;
    }

    public void setChartId(long chartId) {
        this.chartId = chartId;
    }

    @Basic
    @Column(name = "chart_option", nullable = false)
    public String getChartOption() {
        return chartOption;
    }

    public void setChartOption(String chartOption) {
        this.chartOption = chartOption;
    }

    @Basic
    @Column(name = "request_option", nullable = false)
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
