package com.realtimes.service.event;

import com.realtimes.framework.api.event.Event;

public class RemoveTroubleEvent implements Event {

    public static final String TYPE = "REMOVE_TROUBLE";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getName() {
        return "故障解除";
    }
}
