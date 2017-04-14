package com.netcracker.smarthome.model.enums;


import java.util.Calendar;

public enum ChartInterval {
    Live{
        public int getCalendarInterval(){return  Calendar.HOUR_OF_DAY;}
        public int getIntValue(){return -1;}

        @Override
        public String toString() {
            return "Live";
        }
    },
    Hour{
        public int getCalendarInterval(){return  Calendar.HOUR_OF_DAY;}
        public int getIntValue(){return -1;}

        @Override
        public String toString() {
            return "Hour";
        }
    },
    ThreeHour{
        public int getCalendarInterval(){return  Calendar.HOUR_OF_DAY;}
        public int getIntValue(){return -3;}
        @Override
        public String toString() {
            return "3 hour";
        }
    },
    HalfDay{
        public int getCalendarInterval(){return  Calendar.HOUR_OF_DAY;}
        public int getIntValue(){return -12;}
        @Override
        public String toString() {
            return "12 hour";
        }
    },
    Day{
        public int getCalendarInterval(){return  Calendar.DAY_OF_MONTH;}
        public int getIntValue(){return -1;}
        @Override
        public String toString() {
            return "Day";
        }
    },
    Week{
        public int getCalendarInterval(){return  Calendar.DAY_OF_MONTH;}
        public int getIntValue(){return -7;}
        @Override
        public String toString() {
            return "Week";
        }
    },
    TwoWeek{
        public int getCalendarInterval(){return  Calendar.DAY_OF_MONTH;}
        public int getIntValue(){return -14;}
        @Override
        public String toString() {
            return "2 week";
        }
    };

    public abstract int getCalendarInterval();
    public abstract int getIntValue();

    public static ChartInterval getEnum(String value) {
        for(ChartInterval chartInterval : values())
            if(chartInterval.toString().equalsIgnoreCase(value)) return chartInterval;
        throw new IllegalArgumentException();
    }
}
