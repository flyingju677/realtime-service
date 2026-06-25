package com.realtimes.service.event;

import com.realtimes.framework.api.event.Event;

public class NewWarningEvent implements Event {

    public static final String TYPE = "NEW_WARNING";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getName() {
        return "预警发生";
    }
}
