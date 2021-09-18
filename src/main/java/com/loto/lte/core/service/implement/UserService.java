package com.loto.lte.core.service.implement;

import com.loto.lte.core.dao.UserDao;
import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.exception.ValidatorException;
import com.loto.lte.core.service.UserInterface;
import com.loto.lte.core.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserInterface {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    final UserDao userDao;

    UserService(UserDao userDao) {
     this.userDao = userDao;
    }

    @Override
    public JsonObject loadUserByName(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "userName");
        return this.userDao.loadUserByName(param);
    }

    @Override
    public int addNewUser(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id","userName", "firstName", "lastName", "gender", "dateBirth");
        return this.userDao.addNewUser(jsonObject);
    }

    @Override
    public int resetPassword(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id","userName", "password", "isFirstLogin");
        return this.userDao.resetPassword(jsonObject);
    }

    @Override
    public int count() {
        return this.userDao.count();
    }

    @Override
    public int updateUserInfo(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "userID","firstName", "lastName", "gender", "dateBirth");
        return this.userDao.updateUserInfo(jsonObject);
    }

    @Override
    public JsonObject inquiryUserInfoByID(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "userID");
        return this.userDao.inquiryUserInfoByID(param);
    }
}
