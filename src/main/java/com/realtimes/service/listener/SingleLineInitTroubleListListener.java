package com.realtimes.service.listener;

import com.alibaba.fastjson2.JSONObject;
import com.realtimes.framework.api.event.EventContext;
import com.realtimes.framework.api.event.EventListener;
import com.realtimes.framework.api.message.MessageSender;
import com.realtimes.framework.api.subscription.SubscriptionKey;
import com.realtimes.service.event.InitSingleLineTroubleEvent;
import com.realtimes.service.subscription.BusinessSubscriptionKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class SingleLineInitTroubleListListener implements EventListener<InitSingleLineTroubleEvent, String> {

    private final MessageSender messageSender;

    public SingleLineInitTroubleListListener(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public Set<Class<? extends InitSingleLineTroubleEvent>> getAcceptedEventTypes() {
        return Set.of(InitSingleLineTroubleEvent.class);
    }

    @Override
    public void onEvent(InitSingleLineTroubleEvent event, String data, EventContext context) {
        log.info("Processing event: {}, eventContext: {}", event, context);

        if (context == null || context.getSubscriptionKey() == null) {
            log.warn("Invalid event context: event={}, context={}", event, context);
            return;
        }

        SubscriptionKey<?> key = context.getSubscriptionKey();
        if (!(key instanceof SubscriptionKey)) {
            log.warn("Unsupported subscription key type: {}", key.getClass().getName());
            return;
        }

        BusinessSubscriptionKey subscriptionKey = (BusinessSubscriptionKey) key;
        // 获取当前线路的故障列表
        String lineNo = subscriptionKey.getRouteNo();
        //ResultBean resultBean = faultManageFeign.queryOneLineFaultToday(lineNo);

        List<JSONObject> faultList = new ArrayList<>();
        JSONObject faultRecord = new JSONObject();
        faultRecord.put("fault_id", "1001");
        faultRecord.put("fault_type", "车联网故障");
        faultRecord.put("fault_level", 2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        faultRecord.put("fault_time", LocalDateTime.now().format(formatter));
        faultRecord.put("fault_name", "TC1牵引严重故障（模拟）");
        faultList.add(faultRecord);

        //单播到指定session
        messageSender.unicast(context.getSessionId(), JSONObject.toJSONString(faultList));
    }
}

