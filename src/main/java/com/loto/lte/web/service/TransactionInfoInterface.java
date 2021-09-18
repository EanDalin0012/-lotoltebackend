package com.loto.lte.web.service;

import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.dto.JsonObjectArray;
import com.loto.lte.core.exception.ValidatorException;

public interface TransactionInfoInterface {
    int count();
    int doTransaction(JsonObject param) throws ValidatorException;
    JsonObjectArray inquiryDepositTransactionHistory(JsonObject param) throws ValidatorException;
    JsonObjectArray inquiryWithdrawalCashOutTransactionHistory(JsonObject param) throws ValidatorException;
}
