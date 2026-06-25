package com.realtimes.service.event;

import com.realtimes.framework.api.event.Event;

public class InitSingleLineWarningEvent implements Event {

    public static final String TYPE = "INIT_SINGLE_LINE_WARNING";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getName() {
        return "初始化单线路预警列表";
    }
}
