package com.loto.lte.core.dao;

import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.dto.JsonObjectArray;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceInfoDao {
    int save(JsonObject param);
    int updateDeviceInfo(JsonObject param);
    JsonObjectArray inquiry(JsonObject param);
    JsonObjectArray inquiryByUserAgent(JsonObject param);
    int count();
    int deleteDeviceInfo(JsonObject jsonObject);
}
