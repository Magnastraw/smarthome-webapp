package com.netcracker.smarthome.business.chart.options.jsonfields;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

public class Data {
    private long x;
    private BigDecimal y;

    public Data() {
    }

    public Data(long x, BigDecimal y) {
        this.x = x;
        this.y = y;
    }

    public long  getX() {
        return x;
    }

    public void setX(long  x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("x", getX())
                .append("y", getY())
                .toString();
    }
}
