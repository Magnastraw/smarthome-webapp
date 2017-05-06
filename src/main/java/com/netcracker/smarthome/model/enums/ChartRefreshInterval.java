package com.netcracker.smarthome.model.enums;


public enum ChartRefreshInterval {
    Min{
        public long getMillisec(){return 1000*60;}
        @Override
        public String toString() {
            return "1 minute";
        }
    },
    ThreeMin{
        public long getMillisec(){return 1000*60*3;}
        @Override
        public String toString() {
            return "3 minute";
        }
    },
    FiveMin{
        public long getMillisec(){return 1000*60*5;}
        @Override
        public String toString() {
            return "5 minute";
        }
    };

    public abstract long getMillisec();
}
