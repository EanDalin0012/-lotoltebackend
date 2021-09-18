package com.loto.lte.web.service;


import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.exception.ValidatorException;

public interface TransactionInfoDetailsInterface {
    int count();
    int doTransaction(JsonObject param) throws ValidatorException;
}
