package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Entity
@Table(name = "dashboards", schema = "public", catalog = "smarthome_db")
public class Dashboard {
    private long dashboardId;
    private String dashboardName;
    private SmartHome smartHome;

    public Dashboard() {
    }

    public Dashboard(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    public Dashboard(SmartHome smartHome, String dashboardName) {
        this.smartHome = smartHome;
        this.dashboardName =dashboardName;
    }


    @Id
    @Column(name = "dashboard_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dashboard_seq")
    @SequenceGenerator(name = "dashboard_seq", sequenceName = "dashboards_dashboard_id_seq", allocationSize = 1)
    public long getDashboardId() {
        return dashboardId;
    }

    public void setDashboardId(long dashboardId) {
        this.dashboardId = dashboardId;
    }

    @Basic
    @Column(name = "dashboard_name", nullable = false)
    public String getDashboardName() {
        return dashboardName;
    }

    public void setDashboardName(String dashboardName) {
        this.dashboardName = dashboardName;
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

        if (!(o instanceof Dashboard)) return false;

        Dashboard dashboard = (Dashboard) o;

        return new EqualsBuilder()
                .append(getDashboardId(), dashboard.getDashboardId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getDashboardId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("dashboardId", getDashboardId())
                .append("dashboardName",getDashboardName())
                .append("smartHome", getSmartHome())
                .toString();
    }
}
