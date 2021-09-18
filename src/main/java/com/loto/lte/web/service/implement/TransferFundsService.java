package com.loto.lte.web.service.implement;

import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.dto.JsonObjectArray;
import com.loto.lte.core.exception.ValidatorException;
import com.loto.lte.core.util.ValidatorUtil;
import com.loto.lte.web.dao.TransferFundsDao;
import com.loto.lte.web.service.CountInterface;
import com.loto.lte.web.service.TransferFundsInterface;
import org.springframework.stereotype.Service;

@Service
public class TransferFundsService implements TransferFundsInterface, CountInterface
{
    final TransferFundsDao transferFundsDao;
    TransferFundsService(TransferFundsDao transferFundsDao) {
        this.transferFundsDao = transferFundsDao;
    }
    @Override
    public JsonObjectArray inquiryHistoryTransferFundsByUser(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "userID");
        return this.transferFundsDao.inquiryHistoryTransferFundsByUser(jsonObject);
    }

    @Override
    public int withdrawalTransferFunds(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "transferFromAccountID", "receiverAccountID","amount","transferFundsType","currency","userID");
        return this.transferFundsDao.withdrawalTransferFunds(jsonObject);
    }

    @Override
    public int depositTransferFunds(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "transferFromAccountID", "receiverAccountID","amount","transferFundsType","currency","userID");
        return this.transferFundsDao.depositTransferFunds(jsonObject);
    }

    @Override
    public int count() {
        return this.transferFundsDao.count();
    }
}
