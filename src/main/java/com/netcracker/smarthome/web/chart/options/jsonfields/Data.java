package com.netcracker.smarthome.web.chart.options.jsonfields;

import java.math.BigDecimal;

public class Data {
    private String x;
    private BigDecimal y;

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }
}
