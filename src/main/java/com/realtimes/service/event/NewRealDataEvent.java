package com.realtimes.service.event;

import com.realtimes.framework.api.event.Event;

public class NewRealDataEvent implements Event {

    public static final String TYPE = "NEW_REAL_DATA";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getName() {
        return "新实时数据";
    }
}
