package com.loto.lte.web.service.implement;

import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.exception.ValidatorException;
import com.loto.lte.core.util.ValidatorUtil;
import com.loto.lte.web.dao.TransactionInfoDetailsDao;
import com.loto.lte.web.service.TransactionInfoDetailsInterface;
import org.springframework.stereotype.Service;

@Service
public class TransactionInfoDetailsService implements TransactionInfoDetailsInterface {

    final TransactionInfoDetailsDao transactionInfoDetailsDao;
    TransactionInfoDetailsService(TransactionInfoDetailsDao transactionInfoDetailsDao) {
        this.transactionInfoDetailsDao = transactionInfoDetailsDao;
    }

    @Override
    public int count() {
        return this.transactionInfoDetailsDao.count();
    }

    @Override
    public int doTransaction(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "transactionInfoId", "fromAccountIDReference", "fromAccountBalance", "toAccountIDReference","toAccountBalance", "transactionType", "transactionAmount", "currency", "userID");
        return this.transactionInfoDetailsDao.doTransaction(param);
    }
}
