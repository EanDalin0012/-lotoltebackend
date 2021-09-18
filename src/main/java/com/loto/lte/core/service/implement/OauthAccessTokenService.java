package com.loto.lte.core.service.implement;

import com.loto.lte.core.dao.OauthAccessTokenDao;
import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.service.OauthAccessTokenInterface;
import org.springframework.stereotype.Service;

@Service
public class OauthAccessTokenService implements OauthAccessTokenInterface {
    final OauthAccessTokenDao oauthAccessTokenDao;
    OauthAccessTokenService(OauthAccessTokenDao oauthAccessTokenDao) {
        this.oauthAccessTokenDao = oauthAccessTokenDao;
    }

    @Override
    public int deleteOauthAccessTokenByUserName(JsonObject jsonObject) {
        return this.oauthAccessTokenDao.deleteOauthAccessTokenByUserName(jsonObject);
    }

    @Override
    public JsonObject getClientIDUserName(JsonObject jsonObject) {
        return this.oauthAccessTokenDao.getClientIDUserName(jsonObject);
    }

}
