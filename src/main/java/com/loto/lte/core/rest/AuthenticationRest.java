package com.loto.lte.core.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loto.lte.core.constant.MessageCode;
import com.loto.lte.core.constant.StatusCode;
import com.loto.lte.core.dto.Header;
import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.dto.ResponseData;
import org.apache.log4j.Logger;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/oauth2")
public class AuthenticationRest {
    static Logger log = Logger.getLogger(AuthenticationRest.class.getName());

    final TokenStore tokenStore;
    AuthenticationRest(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @GetMapping(value = "/revoke-token")
    public ResponseData<JsonObject> oauthRevokeToken(HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
        JsonObject output = new JsonObject();
        try {
            log.info("========== Start Revoke Token ===========");

            ObjectMapper objectMapper = new ObjectMapper();

            String authHeader = request.getHeader("Authorization");

            log.info("=========== Token Values: " + authHeader);

            if (authHeader != null) {
                String tokenValue = authHeader.replace("Bearer", "").trim();
                OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
                tokenStore.removeAccessToken(accessToken);
            }
            responseData.setBody(header);

            log.info("========= Response Values:" + objectMapper.writeValueAsString(responseData));
            log.info("========== End Invoke Token ===========");


        } catch (Exception e) {
            log.error("======== Get Error Revoke Token Exception ", e);
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(e.getCause().toString());
        }
        responseData.setResult(header);
        return responseData;
    }
}
