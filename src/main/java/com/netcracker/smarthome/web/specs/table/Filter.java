package com.netcracker.smarthome.web.specs.table;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Filter {

    public static boolean filterByMetricType(Object value, Object filter) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        if (((TableEntity)value).isTypeCatalog()) {
            return false;
        }
        if (((TableEntity)value).getMetricSpec().getMetricType() == null)
            return false;
        try {
            return ((TableEntity) value).getMetricSpec().getMetricType().toLowerCase().startsWith(filterText);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean filterByUnit(Object value, Object filter) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        if (((TableEntity)value).isTypeCatalog()) {
            return false;
        }
        if (((TableEntity)value).getMetricSpec().getUnit() == null)
            return false;
        try {
            return ((TableEntity) value).getMetricSpec().getUnit().getUnitName().toLowerCase().startsWith(filterText);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean filterByMinValue(Object value, Object filter) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        if (((TableEntity)value).isTypeCatalog()) {
            return false;
        }
        if (((TableEntity)value).getMetricSpec().getMinValue() == null)
            return false;
        try {
            return ((TableEntity) value).getMetricSpec().getMinValue().toString().startsWith(filterText);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean filterByMaxValue(Object value, Object filter) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        if (((TableEntity)value).isTypeCatalog()) {
            return false;
        }
        if (((TableEntity)value).getMetricSpec().getMaxValue() == null)
            return false;
        try {
            return ((TableEntity) value).getMetricSpec().getMaxValue().toString().startsWith(filterText);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean filterByAssigned(Object value, Object filter) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        if (((TableEntity)value).isTypeCatalog()) {
            return false;
        }
        if (((TableEntity)value).getMetricSpec().getAssignedToObject() == null)
            return false;
        try {
            return ((TableEntity) value).getMetricSpec().getAssignedToObject().startsWith(filterText);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean filterByObjectType(Object value, Object filter) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        if (((TableEntity)value).isTypeCatalog()) {
            return false;
        }
        if (((TableEntity)value).getAlarmSpec().getObjectType() == null)
            return false;
        try {
            return ((TableEntity) value).getAlarmSpec().getObjectType().toLowerCase().startsWith(filterText);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean filterByDate(Object value, Object filter) {
        if (filter == null)
            return true;
        if (value == null)
            return false;
        return DateUtils.truncatedEquals((Date) filter, (Date) value, Calendar.DATE);
    }
}
