package com.loto.lte.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loto.lte.core.constant.MessageCode;
import com.loto.lte.core.constant.StatusCode;
import com.loto.lte.core.dto.Header;
import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.dto.JsonObjectArray;
import com.loto.lte.core.dto.ResponseData;
import com.loto.lte.core.exception.ValidatorException;
import com.loto.lte.core.service.implement.DeviceInfoService;
import com.loto.lte.core.service.implement.UserService;
import com.loto.lte.web.service.implement.AccountService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/my/account")
public class MyAccountRest {
    static Logger log = Logger.getLogger(MyAccountRest.class.getName());

    final DeviceInfoService deviceInfoService;
    final AccountService accountService;
    final UserService userService;
    MyAccountRest(DeviceInfoService deviceInfoService, AccountService accountService, UserService userService) {
        this.deviceInfoService = deviceInfoService;
        this.accountService = accountService;
        this.userService = userService;
    }

    @PostMapping(value = "/v0/inquiry")
    public ResponseData accountInquiry(@RequestBody JsonObject jsonObject, @RequestParam("userId") int userID) {
        log.info("========= Start Account Inquiry ========");
        ResponseData responseData = new ResponseData();
        Header header   = new Header(StatusCode.success, MessageCode.success);
        JsonObject json = new JsonObject();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            log.info("Request Data :"+objectMapper.writeValueAsString(jsonObject));

            if(jsonObject.getInt("userID") == 0 ) {
                header.setResponseCode(StatusCode.notFound);
                header.setResponseMessage("Invalid_UserID");
                responseData.setResult(header);
                return responseData;
            }

            // Inquiry User By ID
            JsonObject userInfoInput = new JsonObject();
            userInfoInput.setInt("userID", userID);
            JsonObject userInfo = userService.inquiryUserInfoByID(userInfoInput);

            // Get Device Info Login
            JsonObject deviceInfo = new JsonObject();
            deviceInfo.setString("userName", userInfo.getString("userName"));
            JsonObjectArray jsonObjectArray = deviceInfoService.inquiry(deviceInfo);


            // Inquiry Account Info By User ID
            JsonObject userInput = new JsonObject();
            userInput.setInt("userID", jsonObject.getInt("userID"));
            JsonObject accountObj = accountService.inquiryAccountByUserID(userInput);

            // Inquiry Sub Account
            int mainAccountId = accountObj.getInt("id");
            log.info("Main Account :"+mainAccountId);
            JsonObject inquirySubAccount = new JsonObject();
            inquirySubAccount.setInt("mainAccountID", mainAccountId);
            JsonObjectArray subAccounts = accountService.inquirySubAccount(inquirySubAccount);

            // Set Return Data
            json.setJsonObjectArray("deviceInfos", jsonObjectArray);
            json.setJsonObject("accountInfo", accountObj);
            json.setJsonObjectArray("subAccounts", subAccounts);

            log.info("Response Data To Client :" +objectMapper.writeValueAsString(json));

        }catch (Exception | ValidatorException e) {
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
            e.printStackTrace();
            return responseData;
        }
        responseData.setResult(header);
        responseData.setBody(json);
        return responseData;
    }
}
