package com.realtimes.service.event;

import com.realtimes.framework.api.event.Event;

public class OnlineEvent implements Event {

    public static final String TYPE = "ONLINE";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getName() {
        return "列车上线";
    }
}
