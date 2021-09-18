package com.loto.lte.web.service;


import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.exception.ValidatorException;

public interface Base64WriteImageInterface {
    int save(JsonObject param) throws ValidatorException;
    int delete(JsonObject param) throws ValidatorException;
    int update(JsonObject param) throws ValidatorException;
    String getResourcesImageById(JsonObject param) throws ValidatorException;
    int count();
}
