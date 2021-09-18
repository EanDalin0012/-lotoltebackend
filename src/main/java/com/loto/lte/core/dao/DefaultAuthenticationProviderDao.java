package com.loto.lte.core.dao;

import com.loto.lte.core.dto.JsonObject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DefaultAuthenticationProviderDao {
    JsonObject getUserByName(JsonObject param);
    JsonObject authenticate(JsonObject jsonObject);
    int lockedUser(JsonObject param);
    int trackSaveUserLock(JsonObject param);
    int trackUpdateUserLock(JsonObject param);
    int trackUpdateUserIsLocked(JsonObject param);
    int updateLoginSuccess(JsonObject param);
    int deleteUserLockCountBYUserName(JsonObject param);
    JsonObject getTrackUserLockByUserName(JsonObject param);
    JsonObject getUserAccountLockByUserName(JsonObject param);
}
