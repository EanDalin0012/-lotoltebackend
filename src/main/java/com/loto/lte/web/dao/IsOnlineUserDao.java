package com.loto.lte.web.dao;

import com.loto.lte.core.dto.JsonObject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IsOnlineUserDao {
    int updateUserToOffline(JsonObject object);
    int updateUserToOnline(JsonObject object);
    JsonObject isOnlineUser(JsonObject object);
}
