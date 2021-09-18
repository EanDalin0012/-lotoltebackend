package com.loto.lte.core.rest;

import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.events.WingNotifyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/api/event")
public class TestingEvent {

    @Inject
    private ApplicationEventPublisher eventPublisher;


    @GetMapping(value = "/e")
    public String index () {
        System.out.println("BotAspect Component beforeExecute");
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("data", "testing");
        eventPublisher.publishEvent(new WingNotifyEvent(jsonObject));
        return "Hello";
    }
}
