package com.loto.lte.core.util;

import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.exception.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class ValidatorUtil {

    private static final Logger logger = LoggerFactory.getLogger(ValidatorUtil.class);

    public static void validate(JsonObject ipParam, String... ipFields) throws ValidatorException {
        for (String sKey : ipFields) {
            if (MRUtil.isEmpty(MRUtil.trim(ipParam.getString(sKey))) || sKey == null) {
                logger.info("Error : " + sKey + " is empty !!!");
                throw new ValidatorException(sKey, "Invalid field[" + sKey + "]. Please add " + sKey + "field");
            }
        }
    }

    public static void validateNull(JsonObject ipParam, String... ipFields) throws ValidatorException {
        for (String sKey : ipFields) {
            if (MRUtil.isNull(ipParam.getString(sKey))) {
                logger.info(String.join(" ", "<<<<< *****", sKey, "is null !!!"));
                throw new ValidatorException(sKey, "field[" + sKey + "] is null");
            }
        }
    }

    public static JsonObject emptyToBeNull(JsonObject ipParam, String... sField) {
        String sTemp = null;
        for (String sKey : sField) {
            sTemp = ipParam.getString(sKey);
            if (MRUtil.isEmpty(sTemp)) {
                ipParam.set(sKey, null);
            }
        }
        return ipParam;
    }

    public static JsonObject makeOptional(JsonObject ipParam, String sType, String... sField) {
        for (String sKey : sField) {
            if (!MRUtil.isEmpty(ipParam.getString(sKey))) {
                continue;
            }
            if (sType.equals("S")) {
                ipParam.set(sKey, "");
            } else if (sType.equals("L")) {
                ipParam.set(sKey, 0L);
            } else if (sType.equals("BD")) {
                ipParam.set(sKey, BigDecimal.ZERO);
            }
        }
        return ipParam;
    }
}
