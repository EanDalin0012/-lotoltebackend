package com.loto.lte.web.dao;

import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.dto.JsonObjectArray;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransferFundsDao {
    JsonObjectArray inquiryHistoryTransferFundsByUser(JsonObject jsonObject);
    int withdrawalTransferFunds(JsonObject jsonObject);
    int depositTransferFunds(JsonObject jsonObject);
    int count();
}
