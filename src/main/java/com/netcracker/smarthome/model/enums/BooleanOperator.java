package com.netcracker.smarthome.model.enums;

<<<<<<< HEAD
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

=======

public enum BooleanOperator {
    AND {
>>>>>>> 19b8056b9f4c5ac40ed4b5a7ec8d43997fb6af58
        public byte getPriority() {
            return 1;
        }
    },
    OR {
<<<<<<< HEAD
        public boolean evaluate(Condition[] args) {
            boolean result = false;
            int n = args.length, i = 0;
            while (i < n && (result = args[i].evaluate()))
                i++;
            return result;
        }

=======
>>>>>>> 19b8056b9f4c5ac40ed4b5a7ec8d43997fb6af58
        public byte getPriority() {
            return 2;
        }
    };

<<<<<<< HEAD
    public abstract boolean evaluate(Condition[] args);

=======
>>>>>>> 19b8056b9f4c5ac40ed4b5a7ec8d43997fb6af58
    public abstract byte getPriority();
}
