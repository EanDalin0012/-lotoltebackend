package com.loto.lte.web.service.implement;

import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.dto.JsonObjectArray;
import com.loto.lte.core.exception.ValidatorException;
import com.loto.lte.core.util.ValidatorUtil;
import com.loto.lte.web.dao.AccountDao;
import com.loto.lte.web.service.AccountInterface;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements AccountInterface {
    final AccountDao accountDao;

    AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public int save(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "accountName", "accountID","accountType","accountStatus","userID", "currency");
        return this.accountDao.save(param);
    }

    @Override
    public JsonObjectArray inquirySubAccount(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "mainAccountID");
        return this.accountDao.inquirySubAccount(param);
    }

    @Override
    public JsonObject inquiryAccountByUserID(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "userID");
        return this.accountDao.inquiryAccountByUserID(param);
    }

    @Override
    public int count() {
        return this.accountDao.count();
    }

    @Override
    public int maxAccountID() {
        return this.accountDao.maxAccountID();
    }

    @Override
    public int updateAccountName(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "accountID", "accountName");
        return this.accountDao.updateAccountName(param);
    }

    @Override
    public JsonObject inquiryUserInfoByAccountID(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "accountID");
        return this.accountDao.inquiryUserInfoByAccountID(param);
    }

    @Override
    public int disableAccount(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "accountID", "status", "userID");
        return this.accountDao.disableAccount(param);
    }

    @Override
    public JsonObject inquiryAccountByID(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "id");
        return this.accountDao.inquiryAccountByID(param);
    }

    @Override
    public int updateAccountBalance(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "accountID", "accountBalance");
        return this.accountDao.updateAccountBalance(param);
    }
}
