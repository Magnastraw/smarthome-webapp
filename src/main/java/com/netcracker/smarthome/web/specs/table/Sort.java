package com.netcracker.smarthome.web.specs.table;

public class Sort {

    public static int sortByName(Object obj1, Object obj2){
        TableEntity object1 = (TableEntity)obj1;
        TableEntity object2 = (TableEntity)obj2;
        if (object1.isTypeCatalog() == object2.isTypeCatalog()) {
            return object1.getName().compareTo(object2.getName());
        }
        else if (object1.isTypeCatalog() && !object2.isTypeCatalog()) {
            return -1;
        }
        else if (!object1.isTypeCatalog() && object2.isTypeCatalog()) {
            return 1;
        }
        else {
            return object1.getName().compareTo(object2.getName());
        }
    }

    public static int sortByMetricType(Object obj1, Object obj2) {
        TableEntity object1 = (TableEntity) obj1;
        TableEntity object2 = (TableEntity) obj2;
        if (object1.isTypeCatalog() && !object2.isTypeCatalog()) {
            return -1;
        }
        else if (!object1.isTypeCatalog() && object2.isTypeCatalog()) {
            return 1;
        }
        else if (!object1.isTypeCatalog() && !object2.isTypeCatalog()) {
            if (object1.getMetricSpec().getMetricType() == null)
                return -1;
            if (object2.getMetricSpec().getMetricType() == null)
                return 1;
            return object1.getMetricSpec().getMetricType().compareTo(object2.getMetricSpec().getMetricType());
        }
        else {
            return object1.getName().compareTo(object2.getName());
        }
    }

    public static int sortByUnit(Object obj1, Object obj2) {
        TableEntity object1 = (TableEntity) obj1;
        TableEntity object2 = (TableEntity) obj2;
        if (object1.isTypeCatalog() && !object2.isTypeCatalog()) {
            return -1;
        }
        else if (!object1.isTypeCatalog() && object2.isTypeCatalog()) {
            return 1;
        }
        else if (!object1.isTypeCatalog() && !object2.isTypeCatalog()) {
            if (object1.getMetricSpec().getUnit().getUnitName() == null)
                return -1;
            if (object2.getMetricSpec().getUnit().getUnitName() == null)
                return 1;
            return object1.getMetricSpec().getUnit().getUnitName().compareTo(object2.getMetricSpec().getUnit().getUnitName());
        }
        else {
            return object1.getName().compareTo(object2.getName());
        }
    }

    public static int sortByMinValue(Object obj1, Object obj2) {
        TableEntity object1 = (TableEntity) obj1;
        TableEntity object2 = (TableEntity) obj2;
        if (object1.isTypeCatalog() && !object2.isTypeCatalog()) {
            return -1;
        }
        else if (!object1.isTypeCatalog() && object2.isTypeCatalog()) {
            return 1;
        }
        else if (!object1.isTypeCatalog() && !object2.isTypeCatalog()) {
            if (object1.getMetricSpec().getMinValue() == null)
                return -1;
            if (object2.getMetricSpec().getMinValue() == null)
                return 1;
            return object1.getMetricSpec().getMinValue().compareTo(object2.getMetricSpec().getMinValue());
        }
        else {
            return object1.getName().compareTo(object2.getName());
        }
    }

    public static int sortByMaxValue(Object obj1, Object obj2) {
        TableEntity object1 = (TableEntity) obj1;
        TableEntity object2 = (TableEntity) obj2;
        if (object1.isTypeCatalog() && !object2.isTypeCatalog()) {
            return -1;
        }
        else if (!object1.isTypeCatalog() && object2.isTypeCatalog()) {
            return 1;
        }
        else if (!object1.isTypeCatalog() && !object2.isTypeCatalog()) {
            if (object1.getMetricSpec().getMaxValue() == null)
                return -1;
            if (object2.getMetricSpec().getMaxValue() == null)
                return 1;
            return object1.getMetricSpec().getMaxValue().compareTo(object2.getMetricSpec().getMaxValue());
        }
        else {
            return object1.getName().compareTo(object2.getName());
        }
    }

    public static int sortByAssigned(Object obj1, Object obj2) {
        TableEntity object1 = (TableEntity) obj1;
        TableEntity object2 = (TableEntity) obj2;
        if (object1.isTypeCatalog() && !object2.isTypeCatalog()) {
            return -1;
        }
        else if (!object1.isTypeCatalog() && object2.isTypeCatalog()) {
            return 1;
        }
        else if (!object1.isTypeCatalog() && !object2.isTypeCatalog()) {
            if (object1.getMetricSpec().getAssignedToObject() == null)
                return -1;
            if (object2.getMetricSpec().getAssignedToObject() == null)
                return 1;
            return object1.getMetricSpec().getAssignedToObject().compareTo(object2.getMetricSpec().getAssignedToObject());
        }
        else {
            return object1.getName().compareTo(object2.getName());
        }
    }

    public static int sortByObjectType(Object obj1, Object obj2) {
        TableEntity object1 = (TableEntity) obj1;
        TableEntity object2 = (TableEntity) obj2;
        if (object1.isTypeCatalog() && !object2.isTypeCatalog()) {
            return -1;
        }
        else if (!object1.isTypeCatalog() && object2.isTypeCatalog()) {
            return 1;
        }
        else if (!object1.isTypeCatalog() && !object2.isTypeCatalog()) {
            if (object1.getAlarmSpec().getObjectType() == null)
                return -1;
            if (object2.getAlarmSpec().getObjectType() == null)
                return 1;
            return object1.getAlarmSpec().getObjectType().compareTo(object2.getAlarmSpec().getObjectType());
        }
        else {
            return object1.getName().compareTo(object2.getName());
        }
    }
}
