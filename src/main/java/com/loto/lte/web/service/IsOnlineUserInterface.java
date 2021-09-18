package com.loto.lte.web.service;


import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.exception.ValidatorException;

public interface IsOnlineUserInterface {
    int updateUserToOffline(JsonObject object) throws ValidatorException;
    int updateUserToOnline(JsonObject object) throws ValidatorException;
    JsonObject isOnlineUser(JsonObject object) throws ValidatorException;

}
