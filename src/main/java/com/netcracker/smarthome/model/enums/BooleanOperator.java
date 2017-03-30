package com.netcracker.smarthome.model.enums;


import com.netcracker.smarthome.business.policy.conditions.Condition;

public enum BooleanOperator {
    AND {
        public boolean evaluate(Condition[] args) {
            boolean result = true;
            int n = args.length, i = 0;
            while (i < n && (result = args[i].evaluate()))
                i++;
            return result;
        }

        public byte getPriority() {
            return 1;
        }
    },
    OR {
        public boolean evaluate(Condition[] args) {
            boolean result = false;
            int n = args.length, i = 0;
            while (i < n && (result = args[i].evaluate()))
                i++;
            return result;
        }

        public byte getPriority() {
            return 2;
        }
    };

    public abstract boolean evaluate(Condition[] args);

    public abstract byte getPriority();
}
