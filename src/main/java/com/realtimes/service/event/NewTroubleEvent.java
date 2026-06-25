package com.realtimes.service.event;

import com.realtimes.framework.api.event.Event;

public class NewTroubleEvent implements Event {

    public static final String TYPE = "NEW_TROUBLE";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getName() {
        return "故障发生";
    }
}
