package com.loto.lte.web.service.implement;

import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.exception.ValidatorException;
import com.loto.lte.core.util.ValidatorUtil;
import com.loto.lte.web.dao.IsOnlineUserDao;
import com.loto.lte.web.service.IsOnlineUserInterface;
import org.springframework.stereotype.Service;

@Service
public class IsOnlineUserService implements IsOnlineUserInterface {

    final IsOnlineUserDao isOnlineUserDao;
    IsOnlineUserService(IsOnlineUserDao isOnlineUserDao) {
        this.isOnlineUserDao = isOnlineUserDao;
    }

    @Override
    public int updateUserToOffline(JsonObject object) throws ValidatorException {
        ValidatorUtil.validate(object, "userID", "isOnline");
        return this.isOnlineUserDao.updateUserToOffline(object);
    }

    @Override
    public int updateUserToOnline(JsonObject object) throws ValidatorException {
        ValidatorUtil.validate(object, "userID", "isOnline");
        return this.isOnlineUserDao.updateUserToOnline(object);
    }

    @Override
    public JsonObject isOnlineUser(JsonObject object) throws ValidatorException {
        ValidatorUtil.validate(object, "userID");
        return this.isOnlineUser(object);
    }
}
