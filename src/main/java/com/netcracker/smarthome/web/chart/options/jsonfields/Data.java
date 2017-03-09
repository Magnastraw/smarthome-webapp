package com.netcracker.smarthome.web.chart.options.jsonfields;

import java.math.BigDecimal;

public class Data {
    private long x;
    private BigDecimal y;

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
}
