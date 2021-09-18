package com.loto.lte.web.dao;

import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.dto.JsonObjectArray;
import com.loto.lte.core.exception.ValidatorException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountDao {
    int save(JsonObject param);
    JsonObjectArray inquirySubAccount(JsonObject param);
    JsonObject inquiryAccountByUserID(JsonObject param);
    int count();
    int maxAccountID();
    int updateAccountName(JsonObject param);
    JsonObject inquiryUserInfoByAccountID(JsonObject param);
    JsonObject inquiryAccountByID(JsonObject param);
    int disableAccount(JsonObject param) throws ValidatorException;
    int updateAccountBalance(JsonObject param) throws ValidatorException;
}
