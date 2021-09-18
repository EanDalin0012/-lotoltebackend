package com.loto.lte.web.service;


import com.loto.lte.core.dto.JsonObject;

public interface IdentifyInterface {
    int save(JsonObject jsonObject);
    int count();
}
