package com.realtimes.service.event;

import com.realtimes.framework.api.event.Event;

public class OfflineEvent implements Event {

    public static final String TYPE = "OFFLINE";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getName() {
        return "列车离线";
    }
}
