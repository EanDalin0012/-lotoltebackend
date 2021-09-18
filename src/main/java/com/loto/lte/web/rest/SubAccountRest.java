package com.loto.lte.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loto.lte.core.constant.MessageCode;
import com.loto.lte.core.constant.StatusCode;
import com.loto.lte.core.dto.Header;
import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.dto.JsonObjectArray;
import com.loto.lte.core.dto.ResponseData;
import com.loto.lte.core.exception.ValidatorException;
import com.loto.lte.web.service.implement.AccountService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/sub/account/v0")
public class SubAccountRest {
    static org.apache.log4j.Logger log = Logger.getLogger(SubAccountRest.class.getName());

    final AccountService accountService;
    SubAccountRest(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "/inquiry")
    public ResponseData accountInquiry(@RequestBody JsonObject jsonObject, @RequestParam("userId") int userID, @RequestParam("lang") String lang) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            int mainAccountId = jsonObject.getInt("mainAccountID");
            if (mainAccountId != 0) {
                log.info("main account id:"+mainAccountId);
                JsonObject inquirySubAccount = new JsonObject();
                inquirySubAccount.setInt("mainAccountID", mainAccountId);
                JsonObjectArray subAccounts = accountService.inquirySubAccount(inquirySubAccount);
                log.info("Sub Account : " + objectMapper.writeValueAsString(subAccounts));
                responseData.setBody(subAccounts);
            } else {
                header.setResponseCode(StatusCode.notFound);
                header.setResponseMessage("Invalid_MainAccountID");
            }
        }catch (Exception | ValidatorException e) {
            e.printStackTrace();
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
            return responseData;
        }
        responseData.setResult(header);
        return responseData;
    }
}
