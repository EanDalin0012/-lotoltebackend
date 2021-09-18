package com.loto.lte.core.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loto.lte.core.constant.DemoProperties;
import com.loto.lte.core.constant.MessageCode;
import com.loto.lte.core.constant.StatusCode;
import com.loto.lte.core.dto.Header;
import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.dto.ResponseData;
import com.loto.lte.core.encryption.EncryptionUtil;
import com.loto.lte.core.exception.ValidatorException;
import com.loto.lte.core.service.implement.DefaultAuthenticationProviderService;
import com.loto.lte.core.service.implement.UserService;
import com.loto.lte.web.service.implement.AccountService;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/api/user")
public class UserRest {
    static Logger log = Logger.getLogger(UserRest.class.getName());

    final UserService userService;
    final AccountService accountService;
    final DefaultAuthenticationProviderService defaultAuthenticationProviderService;
    @Inject
    private ApplicationEventPublisher eventPublisher;

    UserRest(UserService userService, AccountService accountService, DefaultAuthenticationProviderService defaultAuthenticationProviderService) {
        this.userService = userService;
        this.accountService = accountService;
        this.defaultAuthenticationProviderService = defaultAuthenticationProviderService;
    }

    @PostMapping(value = "/v0/loadUser")
    public ResponseData<JsonObject> loadUserByUserName(@RequestBody JsonObject jsonObject, @RequestParam("lang") String lang, @RequestParam("date") String date) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            log.info(objectMapper.writeValueAsString(jsonObject));
            String userName = jsonObject.getString("userName");
            JsonObject deviceInfo = jsonObject.getJsonObject("deviceInfo");

            if(!userName.trim().equals("") || userName == null) {
                JsonObject user = new JsonObject();
                user.setString("userName", userName);
                JsonObject userData = this.userService.loadUserByName(user);
                log.info("User Info :"+objectMapper.writeValueAsString(userData));
                JsonObject accountInput = new JsonObject();
                accountInput.setInt("userID", userData.getInt("id"));
                JsonObject accountInfo = this.accountService.inquiryAccountByUserID(accountInput);

                log.info("account Info:"+objectMapper.writeValueAsString(accountInfo));

                JsonObject data = new JsonObject();
                data.setJsonObject("userInfo", userData);
                data.setJsonObject("accountInfo", accountInfo);

                responseData.setBody(data);

            } else {
                header.setResponseCode(StatusCode.notFound);
                header.setResponseMessage("Invalid_UserName");
            }
            responseData.setResult(header);
            return responseData;
        } catch (Exception | ValidatorException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = "/v0/update/info")
    public ResponseData<JsonObject> updateUserInfo(@RequestBody JsonObject jsonObject, @RequestParam("lang") String lang) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
        try {
            String firstName =  jsonObject.getString("firstName").trim();
            String lastName =  jsonObject.getString("lastName").trim();
            String gender = jsonObject.getString("gender").trim();
            String dateBirth = jsonObject.getString("dateBirth").trim();

            if (firstName ==null || firstName == "") {
                header.setResponseMessage("Invalid_FirstName");
                responseData.setResult(header);
                return responseData;
            }  else if (lastName ==null || lastName.equals("")) {
                header.setResponseMessage("Invalid_LastName");
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
            } else {
                int update = this.userService.updateUserInfo(jsonObject);
                if (update > 0 ) {
                    responseData.setResult(header);
                    responseData.setBody(header);
                    return  responseData;
                }
            }
        } catch (Exception | ValidatorException e) {
            e.printStackTrace();
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
            return responseData;
        }
        return  responseData;
    }


    @PostMapping(value = "/v0/reset-password")
    public ResponseData<JsonObject> resetPassword(@RequestBody JsonObject jsonObject, @RequestParam("lang") String lang) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
        try {
            String userName =  jsonObject.getString("userName").trim();
            int id =  jsonObject.getInt("id");
            String password = jsonObject.getString("password").trim();

            if (id == 0) {
                header.setResponseMessage("Invalid_UserName");
                responseData.setResult(header);
                return responseData;
            }  else if (userName ==null || userName.equals("")) {
                header.setResponseMessage("Invalid_UserName");
                responseData.setResult(header);
                return responseData;
            } else if (password ==null || password.equals("")) {
                header.setResponseMessage("Invalid_Password");
                responseData.setResult(header);
                return responseData;
            }  else {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encryptPassword = passwordEncoder.encode(password);
                JsonObject input = new JsonObject();
                input.set("id", id);
                input.setString("userName", userName);
                input.setString("password", encryptPassword);
                input.setBoolean("isFirstLogin", true);
                int update = this.userService.resetPassword(input);
                if (update > 0 ) {
                    responseData.setResult(header);
                    responseData.setBody(header);
                    return  responseData;
                }
            }
        } catch (Exception | ValidatorException e) {
            e.printStackTrace();
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
            return responseData;
        }
        return  responseData;
    }

    @PostMapping(value = "/v0/change-password")
    public ResponseData<JsonObject> changePassword(@RequestBody JsonObject jsonObject, @RequestParam("lang") String lang) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.notFound, MessageCode.notFound);
        String encodedBase64Key = EncryptionUtil.encodeKey(DemoProperties.secretKey);


        try {
            String userNameEncrypt =  jsonObject.getString("userName").trim();
            String userName = EncryptionUtil.decrypt(userNameEncrypt, encodedBase64Key);
            int userID =  jsonObject.getInt("userID");

            String passwordEncrypt = jsonObject.getString("oldPassword").trim();
            String password        = EncryptionUtil.decrypt(passwordEncrypt, encodedBase64Key);

            String newPasswordEncrypt = jsonObject.getString("newPassword").trim();
            String newPassword = EncryptionUtil.decrypt(newPasswordEncrypt, encodedBase64Key).trim();

            JsonObject userInfoInput = new JsonObject();
            userInfoInput.setString("userName", userName);
            JsonObject userInfo = this.defaultAuthenticationProviderService.getUserObjectByName(userInfoInput);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String _password = "";
            if (userInfo != null) {
                _password = userInfo.getString("password");
            }

            boolean isPasswordMatch = passwordEncoder.matches(password, _password);

            if (userID == 0) {
                header.setResponseMessage("InvalidUserName");
                responseData.setResult(header);
                return responseData;
            }  else if (userName ==null || userName.equals("")) {
                header.setResponseMessage("InvalidUserName");
                responseData.setResult(header);
                return responseData;
            } else if (password ==null || password.equals("")) {
                header.setResponseMessage("PasswordRequire");
                responseData.setResult(header);
                return responseData;
            } else if (newPassword == null || newPassword.equals("")) {
                header.setResponseMessage("NewPasswordRequire");
                responseData.setResult(header);
                return responseData;
            } else if (!isPasswordMatch) {
                header.setResponseMessage("IncorrectPassword");
                responseData.setResult(header);
                return responseData;
            } else {

                String encryptPassword = passwordEncoder.encode(newPassword);
                JsonObject input = new JsonObject();
                input.set("id", userID);
                input.setString("userName", userName);
                input.setString("password", encryptPassword);
                input.setBoolean("isFirstLogin", false);
                int update = this.userService.resetPassword(input);
                if (update > 0 ) {
                    header.setResponseCode(StatusCode.success);
                    header.setResponseMessage(MessageCode.success);
                    responseData.setResult(header);
                    responseData.setBody(header);
                    return  responseData;
                }
            }
        } catch (Exception | ValidatorException e) {
            e.printStackTrace();
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
            return responseData;
        }
        return  responseData;
    }



}
