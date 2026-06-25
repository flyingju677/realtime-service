package com.realtimes.service.event;

import com.realtimes.framework.api.event.Event;

public class InitSingleLineCounterEvent implements Event {

    public static final String TYPE = "INIT_SINGLE_LINE_COUNTER";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getName() {
        return "初始化单线路页面车辆数量统计";
    }
}
