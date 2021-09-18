package com.loto.lte.core.events;

import com.loto.lte.core.dto.JsonObject;
import org.springframework.context.ApplicationEvent;

public class HistoryUserLoginEvent extends ApplicationEvent {
//    public HistoryUserLoginEvent(Object source, JsonObject object) {
//        super(source);
//    }

    public HistoryUserLoginEvent(JsonObject deviceInfo) {
        super(deviceInfo);
    }
}
