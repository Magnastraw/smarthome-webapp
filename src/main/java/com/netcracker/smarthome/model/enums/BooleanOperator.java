package com.netcracker.smarthome.model.enums;

public enum BooleanOperator {
    AND {
        @Override
        public boolean evaluate(Boolean... args) {
            return args[0] && args[1];
        }

        public Boolean completeValue() {
            return false;
        }
    },
    OR {
        @Override
        public boolean evaluate(Boolean... args) {
            return args[0] || args[1];
        }

        public Boolean completeValue() {
            return true;
        }
    },
    NOT {
        @Override
        public boolean evaluate(Boolean... args) {
            return !args[0];
        }

        @Override
        public Boolean completeValue() {
            return null;
        }
    };

    public abstract boolean evaluate(Boolean... args);
    public abstract Boolean completeValue();
}
