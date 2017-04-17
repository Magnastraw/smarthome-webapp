package com.netcracker.smarthome.model.enums;

public enum BooleanOperator {
    AND {
        public boolean evaluate(boolean arg1, boolean arg2) {
            return arg1 && arg2;
        }

        public Boolean completeValue() {
            return false;
        }
    },
    OR {
        public boolean evaluate(boolean arg1, boolean arg2) {
            return arg1 || arg2;
        }

        public Boolean completeValue() {
            return true;
        }
    };

    public abstract boolean evaluate(boolean arg1, boolean arg2);
    public abstract Boolean completeValue();
}
