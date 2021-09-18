package com.loto.lte.web.service;


import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.exception.ValidatorException;

public interface AccountDetailInterface {
    int addSubAccount(JsonObject jsonObject) throws ValidatorException;
    int count();
}
