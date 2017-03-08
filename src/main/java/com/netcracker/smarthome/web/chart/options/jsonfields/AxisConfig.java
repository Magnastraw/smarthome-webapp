package com.netcracker.smarthome.web.chart.options.jsonfields;

import java.util.HashMap;

public class AxisConfig {
    private HashMap<String,String> labels;
    private HashMap<String,String> title;

    public HashMap<String, String> getLabels() {
        return labels;
    }

    public void setLabels(HashMap<String, String> labels) {
        this.labels = labels;
    }

    public HashMap<String, String> getTitle() {
        return title;
    }

    public void setTitle(HashMap<String, String> title) {
        this.title = title;
    }
}
