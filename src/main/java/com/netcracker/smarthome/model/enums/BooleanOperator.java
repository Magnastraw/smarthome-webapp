package com.netcracker.smarthome.model.enums;

public enum BooleanOperator {
    AND {
        public byte getPriority() {
                return 1;
            }
        },
        OR {
            public byte getPriority() {
                return 2;
            }
        };
    public abstract byte getPriority();
}
