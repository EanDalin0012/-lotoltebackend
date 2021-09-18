package com.loto.lte.web.service.implement;

import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.web.dao.IdentifyDao;
import com.loto.lte.web.service.IdentifyInterface;
import org.springframework.stereotype.Service;

@Service
public class IdentifyService implements IdentifyInterface {

    final IdentifyDao identifyDao;
    IdentifyService(IdentifyDao identifyDao) {
        this.identifyDao = identifyDao;
    }

    @Override
    public int save(JsonObject jsonObject) {
        return this.identifyDao.save(jsonObject);
    }

    @Override
    public int count() {
        return this.identifyDao.count();
    }
}
