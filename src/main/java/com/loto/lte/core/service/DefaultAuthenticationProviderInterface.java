package com.loto.lte.core.service;


import com.loto.lte.core.dto.JsonObject;

public interface DefaultAuthenticationProviderInterface {
    JsonObject getUserObjectByName(JsonObject param) throws Exception;
    JsonObject authenticate(JsonObject jsonObject);
}
