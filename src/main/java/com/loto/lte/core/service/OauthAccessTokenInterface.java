package com.loto.lte.core.service;


import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.exception.ValidatorException;

public interface OauthAccessTokenInterface {
    int deleteOauthAccessTokenByUserName(JsonObject jsonObject) throws ValidatorException;
    JsonObject getClientIDUserName(JsonObject jsonObject);
}
