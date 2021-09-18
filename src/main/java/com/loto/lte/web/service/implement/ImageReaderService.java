package com.loto.lte.web.service.implement;

import com.loto.lte.core.dto.JsonObject;
import com.loto.lte.web.dao.ImageReaderDao;
import com.loto.lte.web.service.ImageReaderInterface;
import org.springframework.stereotype.Service;

@Service
public class ImageReaderService implements ImageReaderInterface {

    final ImageReaderDao imageReaderDao;
    ImageReaderService(ImageReaderDao imageReaderDao) {
        this.imageReaderDao = imageReaderDao;
    }

    @Override
    public JsonObject inquiryResourcesID(JsonObject param) {
        return this.imageReaderDao.inquiryResourcesID(param);
    }
}
