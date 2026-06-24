package com.realtimes.service.subscription;

import com.realtimes.framework.api.subscription.SubscriptionKey;

import java.util.Objects;

public class BusinessSubscriptionKey implements SubscriptionKey<String> {

    private final String topic;

    private final String routeNo;

    private final String tripNo;

    public BusinessSubscriptionKey(String topic, String routeNo, String tripNo) {
        this.topic = topic;
        this.routeNo = routeNo;
        this.tripNo = tripNo;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public String getBusinessKey() {
        return routeNo + ":" + tripNo;
    }

    public String getRouteNo() {
        return routeNo;
    }

    public String getTripNo() {
        return tripNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessSubscriptionKey that)) {
            return false;
        }
        return Objects.equals(topic, that.topic)
                && Objects.equals(routeNo, that.routeNo)
                && Objects.equals(tripNo, that.tripNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic, routeNo, tripNo);
    }

    @Override
    public String toString() {
        return getUniqueKey();
    }
}

