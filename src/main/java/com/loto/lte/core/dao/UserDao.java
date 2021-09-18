package com.loto.lte.core.dao;

import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.exception.ValidatorException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    JsonObject loadUserByName(JsonObject param);
    int addNewUser(JsonObject jsonObject) throws ValidatorException;
    int resetPassword(JsonObject jsonObject) throws ValidatorException;
    int count();
    int updateUserInfo(JsonObject jsonObject) throws ValidatorException;
    JsonObject inquiryUserInfoByID(JsonObject param) throws ValidatorException;
}
