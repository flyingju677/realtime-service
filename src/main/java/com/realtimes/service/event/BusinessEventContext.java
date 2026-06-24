package com.realtimes.service.event;

import com.realtimes.framework.api.event.EventContext;
import com.realtimes.framework.api.subscription.SubscriptionKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BusinessEventContext implements EventContext {

    private final String sessionId;

    private final SubscriptionKey<?> subscriptionKey;

    private final Map<String, Object> attributes = new ConcurrentHashMap<>();

    public BusinessEventContext(String sessionId, SubscriptionKey<?> subscriptionKey) {
        this.sessionId = sessionId;
        this.subscriptionKey = subscriptionKey;
    }

    @Override
    public SubscriptionKey<?> getSubscriptionKey() {
        return subscriptionKey;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public <T> T getAttribute(String key, Class<T> type) {
        Object value = attributes.get(key);
        return value == null || type == null ? null : type.cast(value);
    }

    @Override
    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }
}

