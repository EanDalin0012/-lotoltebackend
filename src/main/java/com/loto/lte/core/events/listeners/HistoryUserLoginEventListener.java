package com.loto.lte.core.events.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.dto.JsonObjectArray;
import com.loto.lte.core.events.HistoryUserLoginEvent;
import com.loto.lte.core.exception.ValidatorException;
import com.loto.lte.core.service.implement.DeviceInfoService;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class HistoryUserLoginEventListener implements ApplicationListener<HistoryUserLoginEvent> {
    static org.apache.log4j.Logger log = Logger.getLogger(HistoryUserLoginEventListener.class.getName());

    final DeviceInfoService deviceInfoService;

    HistoryUserLoginEventListener(DeviceInfoService deviceInfoService) {
        this.deviceInfoService = deviceInfoService;
    }

    @Override
    public void onApplicationEvent(HistoryUserLoginEvent event) {
        try {
            log.info("================== Start Listener Keep Device Info ===============");
            ObjectMapper objectMapper =new ObjectMapper();
            JsonObject deviceInfo = (JsonObject) event.getSource();
            JsonObject input = new JsonObject();
            input.setInt("id", (deviceInfoService.count() + 1));
            input.setString("userAgent", deviceInfo.getString("userAgent"));
            input.setString("os", deviceInfo.getString("os"));
            input.setString("device", deviceInfo.getString("device"));
            input.setString("browser", deviceInfo.getString("browser"));
            input.setString("osVersion", deviceInfo.getString("osVersion"));
            input.setString("deviceType", deviceInfo.getString("deviceType"));
            input.setString("browserVersion", deviceInfo.getString("browserVersion"));
            input.setString("orientation", deviceInfo.getString("orientation"));
            input.setString("networkIP", deviceInfo.getString("networkIP"));
            input.setString("userName", deviceInfo.getString("userName"));
            input.setString("date", deviceInfo.getString("date"));
            log.info("device info:"+objectMapper.writeValueAsString(input));

            JsonObjectArray jsonObjectArray = this.deviceInfoService.inquiryByUserAgent(input);
            int size = jsonObjectArray.size();
            if(size > 1) {
                deviceInfoService.deleteDeviceInfo(input);
                deviceInfoService.save(input);
            } else if (size == 1 ) {
                deviceInfoService.updateDeviceInfo(input);
            }else {
                deviceInfoService.save(input);
            }
        } catch (Exception | ValidatorException e) {
            log.info("Exception Error : "+e.getMessage() + ""+e.getCause());
            e.printStackTrace();
        }
    }
}
