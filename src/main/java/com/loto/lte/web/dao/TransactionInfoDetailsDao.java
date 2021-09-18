package com.loto.lte.web.dao;

import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.exception.ValidatorException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionInfoDetailsDao {
    int count();
    int doTransaction(JsonObject param) throws ValidatorException;
}
