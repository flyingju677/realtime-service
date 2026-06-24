package com.realtimes.service.processor;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.realtimes.framework.api.message.MessageProcessor;
import com.realtimes.framework.api.session.SessionContext;
import com.realtimes.framework.api.subscription.SubscriptionManager;
import com.realtimes.service.constant.Constant;
import com.realtimes.service.subscription.BusinessSubscriptionKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BusinessUnsubscribeMessageProcessor implements MessageProcessor<String> {

    private static final String MESSAGE_TYPE = "1025";

    private final SubscriptionManager subscriptionManager;

    public BusinessUnsubscribeMessageProcessor(SubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }

    @Override
    public void process(String message, SessionContext context) {
        log.info("Processing unsubscribe message: {}", message);

        try {
            JSONObject json = JSON.parseObject(message);
            String routeNo = Strings.isBlank(json.getString("route_no")) ? Constant.DEFAULT : json.getString("route_no");
            String tripNo = Strings.isBlank(json.getString("trip_no")) ? Constant.DEFAULT : json.getString("trip_no");
            String topic = Strings.isBlank(json.getString("topic")) ? Constant.DEFAULT : json.getString("topic");
            BusinessSubscriptionKey subscriptionKey = new BusinessSubscriptionKey(topic, routeNo, tripNo);

            //取消订阅
            subscriptionManager.unsubscribe(context.getSessionId(), subscriptionKey);
            log.info("完成[取消订阅]处理: {} by session: {}", subscriptionKey, context.getSessionId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to process unsubscribe message", e);
        }
    }

    @Override
    public String getSupportedMessageType() {
        return MESSAGE_TYPE;
    }
}

