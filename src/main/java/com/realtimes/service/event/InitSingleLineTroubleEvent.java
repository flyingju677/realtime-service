package com.realtimes.service.event;

import com.realtimes.framework.api.event.Event;

public class InitSingleLineTroubleEvent implements Event {

    public static final String TYPE = "INIT_SINGLE_LINE";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getName() {
        return "初始化单线路故障列表";
    }
}

