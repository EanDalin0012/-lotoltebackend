package com.loto.lte.web.service;


import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.dto.JsonObjectArray;
import com.loto.lte.core.exception.ValidatorException;

public interface AccountInterface {
    int save(JsonObject param) throws ValidatorException;
    JsonObjectArray inquirySubAccount(JsonObject param) throws ValidatorException;
    JsonObject inquiryAccountByUserID(JsonObject param) throws ValidatorException;
    int count();
    int maxAccountID();
    int updateAccountName(JsonObject param) throws ValidatorException;
    JsonObject inquiryUserInfoByAccountID(JsonObject param) throws ValidatorException;
    int disableAccount(JsonObject param) throws ValidatorException;
    JsonObject inquiryAccountByID(JsonObject param) throws ValidatorException;
    int updateAccountBalance(JsonObject param) throws ValidatorException;
}
