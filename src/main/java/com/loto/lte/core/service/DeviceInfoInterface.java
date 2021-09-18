package com.loto.lte.core.service;


import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.dto.JsonObjectArray;
import com.loto.lte.core.exception.ValidatorException;

public interface DeviceInfoInterface {
    int save(JsonObject param);
    int updateDeviceInfo(JsonObject param);
    JsonObjectArray inquiry(JsonObject param);
    JsonObjectArray inquiryByUserAgent(JsonObject param) throws ValidatorException;
    int count();
    int deleteDeviceInfo(JsonObject jsonObject) throws ValidatorException;

}
