package com.loto.lte.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loto.lte.core.constant.DemoProperties;
import com.loto.lte.core.constant.MessageCode;
import com.loto.lte.core.constant.StatusCode;
import com.loto.lte.core.dto.Header;
import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.dto.ResponseData;
import com.loto.lte.core.encryption.EncryptionUtil;
import com.loto.lte.core.exception.ValidatorException;
import com.loto.lte.core.service.implement.UserService;
import com.loto.lte.core.util.CurrentDateUtil;
import com.loto.lte.web.constant.AccountStatus;
import com.loto.lte.web.service.implement.AccountService;
import com.loto.lte.web.service.implement.IdentifyService;
import org.apache.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/account")
public class AccountRest {
    static Logger log = Logger.getLogger(AccountRest.class.getName());

    final AccountService accountService;
    final UserService userService;
    final PlatformTransactionManager transactionManager;
//    final AccountDetailService accountDetailService;
    final IdentifyService identifyService;

    AccountRest(AccountService accountService, UserService userService, PlatformTransactionManager platformTransactionManager, IdentifyService identifyService) {
        this.userService = userService;
        this.accountService = accountService;
        this.transactionManager = platformTransactionManager;
        this.identifyService = identifyService;
    }

    @PostMapping(value = "/v0/save")
    public ResponseData save(@RequestBody JsonObject jsonObject, @RequestParam("userId") int userID, @RequestParam("date") String date) {
        ResponseData responseData = new ResponseData();
        ObjectMapper objectMapper = new ObjectMapper();
        Header header = new Header(StatusCode.notFound, MessageCode.success);
        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try{
            log.info(objectMapper.writeValueAsString(jsonObject));
            String encodedBase64Key = EncryptionUtil.encodeKey(DemoProperties.secretKey);

            // Personal Information
            JsonObject personalAccountInfo = jsonObject.getJsonObject("personalInformation");
            JsonObject userInfo = jsonObject.getJsonObject("userInfo");
            JsonObject identifyInformation = jsonObject.getJsonObject("identifyInfo");
            JsonObject accountInfo = jsonObject.getJsonObject("accountInfo");
            JsonObject mainAccountInfo = jsonObject.getJsonObject("mainAccountInfo");

            int mainAccountID = mainAccountInfo.getInt("id");

            // validation
            String firstName        =  personalAccountInfo.getString("firstName").trim();
            String lastName         =  personalAccountInfo.getString("lastName").trim();
            String gender           = personalAccountInfo.getString("gender").trim();
            String dateBirth        = personalAccountInfo.getString("dateBirth").trim();
            String phoneNumber      = personalAccountInfo.getString("phoneNumber").trim();
            String otherPhoneNumber = personalAccountInfo.getString("otherPhone").trim();
            String address          = personalAccountInfo.getString("address").trim();
            String remark           = jsonObject.getString("remark").trim();

            String userNameDecrypt  = userInfo.getString("userName");
            String userName         = EncryptionUtil.decrypt(userNameDecrypt, encodedBase64Key);

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String passwordDecrypt  = userInfo.getString("password");
            String password         = EncryptionUtil.decrypt(passwordDecrypt, encodedBase64Key);
            String passwordEncoder  = bCryptPasswordEncoder.encode(password);

            String accountType      = accountInfo.getString("accountType").trim();
            String  currency        = accountInfo.getString("currency").trim();

            int personalAccountInfoResourceID= personalAccountInfo.getInt("resourceID");

            JsonObject checkUserNameInput = new JsonObject();
            checkUserNameInput.setString("userName", userName);
            JsonObject userInquiry = this.userService.loadUserByName(checkUserNameInput);

            if (userInquiry != null) {
                header.setResponseMessage("User_Had");
                responseData.setResult(header);
                return responseData;
            }
            if (firstName ==null || firstName == "") {
                header.setResponseMessage("Invalid_FirstName");
                responseData.setResult(header);
                return responseData;
            } else if (lastName ==null || lastName.equals("")) {
                header.setResponseMessage("Invalid_LastName");
                responseData.setResult(header);
                return responseData;
            } else if (password ==null || password.equals("")) {
                header.setResponseMessage("Invalid_Password");
                responseData.setResult(header);
                return responseData;
            } else if (gender ==null || gender.equals("")) {
                header.setResponseMessage("Invalid_Gender");
                responseData.setResult(header);
                return responseData;
            } else if (dateBirth ==null || dateBirth.equals("")) {
                header.setResponseMessage("Invalid_DB");
                responseData.setResult(header);
                return responseData;
            } else if (userName ==null || userName.equals("")) {
                header.setResponseMessage("Invalid_UserName");
                responseData.setResult(header);
                return responseData;
            }  else if (accountType ==null || accountType.equals("")) {
                header.setResponseMessage("Invalid_AccountType");
                responseData.setResult(header);
                return responseData;
            } else if (currency ==null || currency.equals("")) {
                header.setResponseMessage("Invalid_Currency");
                responseData.setResult(header);
                return responseData;
            } else if (mainAccountID <= 0) {
                header.setResponseMessage("Invalid_MainAccountID");
                responseData.setResult(header);
                return responseData;
            }

            JsonObject personalInform = new JsonObject();
            int personalInformID = this.userService.count() + 1;
            personalInform.setInt("id", personalInformID);
            personalInform.setString("userName", userName);
            personalInform.setString("password", passwordEncoder);
            personalInform.setString("firstName", firstName);
            personalInform.setString("lastName",lastName);
            personalInform.setString("gender", gender);
            personalInform.setString("dateBirth", dateBirth);
            personalInform.setString("phoneNumber", phoneNumber);
            personalInform.setString("otherPhoneNumber", otherPhoneNumber);
            personalInform.setString("identifyInfoID", identifyInformation.getString("identifyID"));
            personalInform.setInt("identifyInfoResourceId", identifyInformation.getInt("resourceID"));
            personalInform.setInt("resourceID", personalAccountInfoResourceID);
            personalInform.setString("remark", remark);
            personalInform.setInt("userID", userID);
            personalInform.setString("address", address);
            personalInform.setString("date", CurrentDateUtil.get());

            log.info("personalInform :"+ objectMapper.writeValueAsString(personalInform));
            int savePersonalInfo = this.userService.addNewUser(personalInform);

            JsonObject accountInfoInput = new JsonObject();
            int accountInfoId = this.accountService.count() + 1;
            String accountID =  generateAccountID(this.accountService.maxAccountID() + 1);


            accountInfoInput.setInt("id", accountInfoId);
            accountInfoInput.setString("accountName", firstName + " "+lastName);
            accountInfoInput.setString("accountID", accountID);
            accountInfoInput.setString("accountType", accountType);
            accountInfoInput.setInt("accountBalance", 0);
            accountInfoInput.setString("accountStatus", AccountStatus.active);
            accountInfoInput.setInt("userID", personalInformID);
            accountInfoInput.setInt("createBy", userID);
            accountInfoInput.setString("currency", currency);
            accountInfoInput.setString("date", CurrentDateUtil.get());
            accountInfoInput.setInt("baseOnID", mainAccountID);

            log.info("accountInfo :"+ objectMapper.writeValueAsString(accountInfoInput));
            int saveAccountInfo = this.accountService.save(accountInfoInput);

            JsonObject jsonObj = new JsonObject();

            if(savePersonalInfo > 0 && saveAccountInfo > 0 ) {
                jsonObj.setString("status", "Y");
                jsonObj.setString("accountName", accountInfoInput.getString("accountName"));
                jsonObj.setString("accountID", accountID);
                jsonObj.setString("userName", userName);
                jsonObj.setString("password", password);
                transactionManager.commit(transactionStatus);
                responseData.setBody(jsonObj);
            } else {
                jsonObj.setString("status", "N");
                transactionManager.rollback(transactionStatus);
                responseData.setBody(jsonObj);
            }

        }catch (Exception | ValidatorException e) {
            e.printStackTrace();
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
            transactionManager.rollback(transactionStatus);
            return responseData;
        }
        header.setResponseCode(StatusCode.success);
        responseData.setResult(header);
        return responseData;
    }

    @PostMapping(value = "/v0/update/accountName")
    public ResponseData updateAccountName(@RequestBody JsonObject jsonObject, @RequestParam("userId") int userID, @RequestParam("lang") String lang) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.notFound, MessageCode.notFound);
        try {
            String accountName = jsonObject.getString("accountName");
            String accountID = jsonObject.getString("accountID");
            int id = jsonObject.getInt("id");
            if (id == 0) {
                header.setResponseMessage("Invalid_AccountID");
                responseData.setResult(header);
                return responseData;
            } else if (accountName ==null || accountName == "") {
                header.setResponseMessage("Invalid_AccountName");
                responseData.setResult(header);
                return responseData;
            } else if (accountID ==null || accountID == "") {
                header.setResponseMessage("Invalid_AccountID");
                responseData.setResult(header);
                return responseData;
            } else {
                int update = this.accountService.updateAccountName(jsonObject);
                if (update > 0) {
                    header.setResponseCode(StatusCode.success);
                    header.setResponseMessage( MessageCode.notFound);
                    responseData.setBody(header);
                    responseData.setResult(header);
                    return responseData;
                }
            }
        }catch (Exception | ValidatorException e) {
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
            e.printStackTrace();
            return responseData;
        }
        return responseData;
    }

    @PostMapping(value = "/v0/inquiry/user-info")
    public ResponseData inquiryUserByAccountID(@RequestBody JsonObject jsonObject, @RequestParam("userId") int userID, @RequestParam("lang") String lang) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
        try {
            JsonObject jsonObj = this.accountService.inquiryUserInfoByAccountID(jsonObject);

            JsonObject input = new JsonObject();
            input.setInt("userID", jsonObj.getInt("userID"));
            JsonObject userInfo = this.userService.inquiryUserInfoByID(input);
            responseData.setBody(userInfo);

        }catch (Exception | ValidatorException e) {
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
            e.printStackTrace();
            return responseData;
        }

        responseData.setResult(header);
        return responseData;
    }


    @PostMapping(value = "/v0/disable-account")
    public ResponseData disableAccount(@RequestBody JsonObject jsonObject, @RequestParam("userId") int userID, @RequestParam("lang") String lang) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.notFound, MessageCode.notFound);
        try {
            String accountID = jsonObject.getString("accountID");
            int id = jsonObject.getInt("id");
            String status = jsonObject.getString("status");

            if(id <= 0 ) {
                header.setResponseMessage("Invalid_AccountID");
                responseData.setResult(header);
                return responseData;
            } else if (accountID ==null || accountID == "") {
                header.setResponseMessage("Invalid_AccountID");
                responseData.setResult(header);
                return responseData;
            } else if (status ==null || status == "") {
                header.setResponseMessage("Invalid_AccountStatus");
                responseData.setResult(header);
                return responseData;
            } else {
                JsonObject input = new JsonObject();
                input.setInt("userID", userID);
                input.setString("accountID", accountID);
                input.setString("status", status);
                input.setInt("id", id);
                input.setString("remark", jsonObject.getString("remark"));

                int update = this.accountService.disableAccount(input);
                if(update > 0) {
                    header.setResponseCode(StatusCode.success);
                    header.setResponseMessage(MessageCode.success);
                    responseData.setResult(header);
                    responseData.setBody(header);
                    return responseData;
                }
            }

        }catch (Exception | ValidatorException e) {
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
            e.printStackTrace();
            return responseData;
        }

        responseData.setResult(header);
        return responseData;
    }


    private String generateAccountID(int accountID) {
        int accountLength = String.valueOf(accountID).length();

        System.out.println(accountLength);
        String account = "";
        for (int i = 0 ; i < 9 - accountLength; i++) {
            account += "0";
        }
        String accountId = account + accountID;
        if(accountLength == 9 && accountId == "999999999") {
            accountId = "1000000000";
        }
        System.out.println(accountId);
        System.out.println(accountId.length());
        return accountId;
    }
}
