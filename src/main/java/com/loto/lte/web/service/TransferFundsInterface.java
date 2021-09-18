package com.loto.lte.web.service;

import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.dto.JsonObjectArray;
import com.loto.lte.core.exception.ValidatorException;

public interface TransferFundsInterface {
    JsonObjectArray inquiryHistoryTransferFundsByUser(JsonObject jsonObject) throws ValidatorException;
    int withdrawalTransferFunds(JsonObject jsonObject) throws ValidatorException;
    int depositTransferFunds(JsonObject jsonObject) throws ValidatorException;
}
