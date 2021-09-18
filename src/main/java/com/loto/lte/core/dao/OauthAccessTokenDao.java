package com.loto.lte.core.dao;

import com.loto.lte.core.dto.JsonObject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OauthAccessTokenDao {
    int deleteOauthAccessTokenByUserName(JsonObject jsonObject);
    JsonObject getClientIDUserName(JsonObject jsonObject);
}
