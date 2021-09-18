package com.loto.lte.web.service.implement;

import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.exception.ValidatorException;
import com.loto.lte.core.util.ValidatorUtil;
import com.loto.lte.web.dao.AccountDetailDao;
import com.loto.lte.web.service.AccountDetailInterface;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailService implements AccountDetailInterface {

    final AccountDetailDao accountDetailDao;
    AccountDetailService(AccountDetailDao accountDetailDao) {
        this.accountDetailDao = accountDetailDao;
    }

    @Override
    public int addSubAccount(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "mainAccountID", "subAccountID");
        return this.accountDetailDao.addSubAccount(jsonObject);
    }

    @Override
    public int count() {
        return this.accountDetailDao.count();
    }
}
