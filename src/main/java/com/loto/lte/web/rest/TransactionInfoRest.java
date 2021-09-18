package com.loto.lte.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loto.lte.core.common.TxrIDKeyGenerator;
import com.loto.lte.core.constant.MessageCode;
import com.loto.lte.core.constant.StatusCode;
import com.loto.lte.core.dto.Header;
import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.dto.JsonObjectArray;
import com.loto.lte.core.dto.ResponseData;
import com.loto.lte.core.exception.ValidatorException;
import com.loto.lte.core.util.CurrentDateUtil;
import com.loto.lte.web.constant.TransactionType;
import com.loto.lte.web.service.implement.AccountService;
import com.loto.lte.web.service.implement.TransactionInfoDetailsService;
import com.loto.lte.web.service.implement.TransactionInfoService;
import org.apache.log4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/transactionInfo")
public class TransactionInfoRest {
    static org.apache.log4j.Logger log = Logger.getLogger(TransactionInfoRest.class.getName());

    final PlatformTransactionManager transactionManager;
    final AccountService accountService;
    final TransactionInfoService transactionInfoService;
    final TransactionInfoDetailsService transactionInfoDetailsService;

    TransactionInfoRest(PlatformTransactionManager platformTransactionManager, AccountService accountService, TransactionInfoService transactionInfoService, TransactionInfoDetailsService transactionInfoDetailsService) {
        this.transactionManager = platformTransactionManager;
        this.accountService = accountService;
        this.transactionInfoService = transactionInfoService;
        this.transactionInfoDetailsService = transactionInfoDetailsService;
    }

    @PostMapping(value = "/v0/doTransaction")
    public ResponseData doTransaction(@RequestBody JsonObject jsonObject, @RequestParam("userId") int userID, @RequestParam("lang") String lang) {
        log.info("========= Start Do Transaction ==========");
        ResponseData responseData = new ResponseData();
        ObjectMapper objectMapper = new ObjectMapper();
        Header header = new Header(StatusCode.notFound, MessageCode.notFound);
        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            String fromAccountID             = jsonObject.getString("fromAccountID");
            int fromAccountIDReference       = jsonObject.getInt("fromAccountId");
            String toAccountID               = jsonObject.getString("toAccountID");
            int toAccountIDReference         = jsonObject.getInt("toAccountId");
            String transactionType           = jsonObject.getString("transactionType");
            int transactionAmount            = jsonObject.getInt("transactionAmount");
            String currency                  = jsonObject.getString("currency");
            String remark                    = jsonObject.getString("remark");
            String txID                      = jsonObject.getString("txID");

            if (fromAccountIDReference <= 0 || fromAccountID == null || fromAccountID.equals("")) {
                header.setResponseMessage("Require_FromAccountID");
                responseData.setResult(header);
                return responseData;
            } else if (toAccountIDReference<=0 || toAccountID == null || toAccountID.equals("")) {
                header.setResponseMessage("Require_ToAccountID");
                responseData.setResult(header);
                return responseData;
            } else if (transactionType == null || transactionType.equals("")) {
                header.setResponseMessage("Require_TransactionType");
                responseData.setResult(header);
                return responseData;
            } else if(transactionAmount <= 0) {
                header.setResponseMessage("Require_TransactionAmount");
                responseData.setResult(header);
                return responseData;
            } else if (currency == null || currency.equals("")) {
                header.setResponseMessage("Require_Currency");
                responseData.setResult(header);
                return responseData;
            } else {

                JsonObject fromAccountInfoInput = new JsonObject();
                fromAccountInfoInput.setInt("id", fromAccountIDReference);
                JsonObject fromAccountInfo = accountService.inquiryAccountByID(fromAccountInfoInput);
                log.info("accountInfo:"+objectMapper.writeValueAsString(fromAccountInfo));

                if (transactionAmount > fromAccountInfo.getInt("accountBalance")) {
                    header.setResponseMessage("Account_Balance_Not_Enough");
                    responseData.setResult(header);
                    return responseData;
                }

                JsonObject toAccountInfoInput = new JsonObject();
                toAccountInfoInput.setInt("id", toAccountIDReference);
                JsonObject toAccountInfo = accountService.inquiryAccountByID(toAccountInfoInput);
                log.info("toAccountInfo:"+objectMapper.writeValueAsString(toAccountInfo));

                JsonObject transactionInfo = new JsonObject();
                int transactionInfoId  = this.transactionInfoService.count() + 1;
                transactionInfo.setInt("id", transactionInfoId);
                transactionInfo.setString("fromAccountID", fromAccountID);
                transactionInfo.setInt("fromAccountIDReference", fromAccountIDReference);
                transactionInfo.setString("toAccountID", toAccountID);
                transactionInfo.setInt("toAccountIDReference", toAccountIDReference);
                transactionInfo.setString("transactionType", transactionType);
                transactionInfo.setInt("transactionAmount", transactionAmount);
                transactionInfo.setString("currency", currency);
                transactionInfo.setString("remark", remark);
                transactionInfo.setInt("userID", userID);
                transactionInfo.setString("date", CurrentDateUtil.get());
                transactionInfo.setString("txID", TxrIDKeyGenerator.generate(txID));

                log.info("transactionInfo:"+objectMapper.writeValueAsString(transactionInfo));
                int saveTransactionInfo = this.transactionInfoService.doTransaction(transactionInfo);
                log.info("saveTransactionInfo:"+saveTransactionInfo);

                JsonObject transactionInfoDetails = new JsonObject();
                int transactionInfoDetailsId  = this.transactionInfoDetailsService.count() + 1;
                transactionInfoDetails.setInt("id", transactionInfoDetailsId);
                transactionInfoDetails.setInt("transactionInfoId", transactionInfoId);
                transactionInfoDetails.setInt("fromAccountIDReference", fromAccountIDReference);
                transactionInfoDetails.setInt("fromAccountBalance", fromAccountInfo.getInt("accountBalance"));
                transactionInfoDetails.setInt("toAccountIDReference", toAccountIDReference);
                transactionInfoDetails.setInt("toAccountBalance", toAccountInfo.getInt("accountBalance"));
                transactionInfoDetails.setString("transactionType",transactionType);
                transactionInfoDetails.setInt("transactionAmount", transactionAmount);
                transactionInfoDetails.setString("currency", currency);
                transactionInfoDetails.setInt("userID", userID);

                log.info("transactionInfoDetails:"+objectMapper.writeValueAsString(transactionInfoDetails));
                int saveTransactionInfoDetails = this.transactionInfoDetailsService.doTransaction(transactionInfoDetails);
                log.info("saveTransactionInfoDetails:"+saveTransactionInfoDetails);

                JsonObject updateFromAccountBalanceInput = new JsonObject();
                int fromAccountBalance = fromAccountInfo.getInt("accountBalance") - transactionAmount;
                updateFromAccountBalanceInput.setInt("id", fromAccountInfo.getInt("id"));
                updateFromAccountBalanceInput.setString("accountID", fromAccountInfo.getString("accountID"));
                updateFromAccountBalanceInput.setInt("accountBalance", fromAccountBalance);

                log.info("updateFromAccountBalanceInput:"+objectMapper.writeValueAsString(updateFromAccountBalanceInput));
                int updateFromAcc = this.accountService.updateAccountBalance(updateFromAccountBalanceInput);
                log.info("updateFromAcc:"+updateFromAcc);


                JsonObject updateToAccountBalanceInput = new JsonObject();
                int toAccountBalance = toAccountInfo.getInt("accountBalance") + transactionAmount;
                updateToAccountBalanceInput.setInt("id", toAccountInfo.getInt("id"));
                updateToAccountBalanceInput.setString("accountID", toAccountInfo.getString("accountID"));
                updateToAccountBalanceInput.setInt("accountBalance", toAccountBalance);

                log.info("updateToAccountBalanceInput:"+objectMapper.writeValueAsString(updateToAccountBalanceInput));
                int updateToAcc = this.accountService.updateAccountBalance(updateToAccountBalanceInput);
                log.info("updateToAcc:"+updateToAcc);

                if (saveTransactionInfo > 0 && saveTransactionInfoDetails > 0 && updateFromAcc > 0 && updateToAcc > 0 ) {
                    // success
                    header.setResponseCode(StatusCode.success);
                    header.setResponseMessage(MessageCode.success);
                    responseData.setBody(header);
                    responseData.setResult(header);
                    transactionManager.commit(transactionStatus);
                    return responseData;
                }
            }
        }catch (Exception | ValidatorException e) {
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
            transactionManager.rollback(transactionStatus);
            log.error(String.valueOf(e));
            return responseData;
        }
        responseData.setResult(header);
        return responseData;
    }

    @PostMapping(value = "/v0/inquiry-transaction-history")
    public ResponseData inquiryTransactionHistory(@RequestBody JsonObject jsonObject, @RequestParam("userId") int userID, @RequestParam("lang") String lang) {
        log.info("============ Start Inquiry Transaction History ================");

        ResponseData responseData = new ResponseData();
        ObjectMapper objectMapper = new ObjectMapper();
        Header header = new Header(StatusCode.success, MessageCode.success);

        try {
            log.info("Request Data "+objectMapper.writeValueAsString(jsonObject));

            if(jsonObject.getInt("id") <= 0 || jsonObject.getString("accountID") == null || jsonObject.getString("accountID").equals("")) {
                header.setResponseMessage("Require_AccountID");
                responseData.setResult(header);
                return responseData;
            }

            // Inquiry Transaction History Deposit
            JsonObject depositMoneyInput = new JsonObject();
            depositMoneyInput.setInt("id", jsonObject.getInt("id"));
            depositMoneyInput.setString("accountID", jsonObject.getString("accountID"));
            depositMoneyInput.setString("transactionType", TransactionType.depositMoney);
            JsonObjectArray depositMoneys = this.transactionInfoService.inquiryDepositTransactionHistory(depositMoneyInput);

            // Inquiry Transaction History Withdrawal Cash Out
            JsonObject withdrawalCashOutInput = new JsonObject();
            withdrawalCashOutInput.setInt("id", jsonObject.getInt("id"));
            withdrawalCashOutInput.setString("accountID", jsonObject.getString("accountID"));
            withdrawalCashOutInput.setString("transactionType", TransactionType.withdrawalCashOut);
            JsonObjectArray withdrawalCashOuts = this.transactionInfoService.inquiryWithdrawalCashOutTransactionHistory(withdrawalCashOutInput);

            JsonObject object = new JsonObject();
            object.setJsonObjectArray("depositMoneys", depositMoneys);
            object.setJsonObjectArray("withdrawalCashOuts", withdrawalCashOuts);

            responseData.setResult(header);
            responseData.setBody(object);

            log.info("Response Data Client :"+objectMapper.writeValueAsString(responseData));

        }catch (Exception | ValidatorException e) {
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
            log.error(String.valueOf(e));
            return responseData;
        }
        return  responseData;
    }
}
