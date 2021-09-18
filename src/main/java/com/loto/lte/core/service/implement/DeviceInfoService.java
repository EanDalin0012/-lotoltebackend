package com.loto.lte.core.service.implement;

import com.loto.lte.core.dao.DeviceInfoDao;
import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.dto.JsonObjectArray;
import com.loto.lte.core.exception.ValidatorException;
import com.loto.lte.core.service.DeviceInfoInterface;
import com.loto.lte.core.util.ValidatorUtil;
import org.springframework.stereotype.Service;

@Service
public class DeviceInfoService implements DeviceInfoInterface {
    final DeviceInfoDao deviceInfoDao;

    DeviceInfoService(DeviceInfoDao deviceInfoDao) {
        this.deviceInfoDao = deviceInfoDao;
    }
    @Override
    public int save(JsonObject param) {
        return this.deviceInfoDao.save(param);
    }

    @Override
    public int updateDeviceInfo(JsonObject param) {
        return this.deviceInfoDao.updateDeviceInfo(param);
    }

    @Override
    public JsonObjectArray inquiry(JsonObject param) {
        return this.deviceInfoDao.inquiry(param);
    }

    @Override
    public JsonObjectArray inquiryByUserAgent(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "userName", "userAgent");
        return this.deviceInfoDao.inquiryByUserAgent(param);
    }

    @Override
    public int count() {
        return this.deviceInfoDao.count();
    }

    @Override
    public int deleteDeviceInfo(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "userName", "userAgent");
        return this.deviceInfoDao.deleteDeviceInfo(jsonObject);
    }
}
