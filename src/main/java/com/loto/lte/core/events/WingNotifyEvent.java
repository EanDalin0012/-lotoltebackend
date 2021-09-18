package com.loto.lte.core.events;

import org.springframework.context.ApplicationEvent;

public class WingNotifyEvent extends ApplicationEvent {
    public WingNotifyEvent(Object source) {
        super(source);
    }
}
