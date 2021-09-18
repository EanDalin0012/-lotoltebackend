package com.loto.lte.web.rest;

import com.loto.lte.core.constant.MessageCode;
import com.loto.lte.core.constant.Status;
import com.loto.lte.core.constant.StatusCode;
import com.loto.lte.core.dto.Header;
import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.core.dto.ResponseData;
import com.loto.lte.core.exception.ValidatorException;
import com.loto.lte.web.service.implement.Base64WriteImageService;
import com.loto.lte.web.util.Base64ImageUtil;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/base64/image/v0")
public class Base64WriteImageRest {
    static org.apache.log4j.Logger log = Logger.getLogger(Base64WriteImageRest.class.getName());

    final Base64WriteImageService base64WriteImageService;

    public Base64WriteImageRest(Base64WriteImageService base64WriteImageService) {
        this.base64WriteImageService = base64WriteImageService;
    }

    @PostMapping(value = "/write")
    public ResponseData<JsonObject> index(@RequestBody JsonObject jsonObject, @RequestParam("userId") int userID, @RequestParam("lang") String lang) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
        try {
            String path = "/uploads/images";
            String base64 = jsonObject.getString("base64");
            String id = jsonObject.getString("id");
            String fileExtension = jsonObject.getString("file_extension");

            log.info("path:" + path + ",id" + id + ",name:" + id + ",extension:" + fileExtension);

            String basePath = Base64ImageUtil.decodeToImage(path, base64, id, fileExtension);
            JsonObject input = new JsonObject();
            int resourceId = this.base64WriteImageService.count() + 1;
            if (!basePath.equals("")) {
                log.info("data:" + jsonObject);
                input.setInt("id", resourceId);
                input.setString("fileName", id);
                input.setInt("fileSize", jsonObject.getInt("file_size"));
                input.setString("fileType", jsonObject.getString("file_type"));
                input.setString("fileExtension", jsonObject.getString("file_extension"));
                input.setString("originalName", jsonObject.getString("file_name"));
                input.setString("fileSource", basePath);
                input.setString("status", Status.active);
                input.setInt("userID", userID);
                int save = base64WriteImageService.save(input);
                if (save > 0) {
                    JsonObject jsonObj = new JsonObject();
                    jsonObj.setInt("resourceID", resourceId);
                    jsonObj.setString("status", "Y");
                    responseData.setBody(jsonObj);
                    responseData.setResult(header);
                    return responseData;
                }
            } else {
                JsonObject jsonObj = new JsonObject();
                jsonObj.setString("status", "N");
                responseData.setBody(jsonObj);
                responseData.setResult(header);
                return responseData;
            }
        }catch (Exception | ValidatorException e) {
            e.printStackTrace();
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
            e.printStackTrace();
            return responseData;
        }
        return responseData;
    }

}
