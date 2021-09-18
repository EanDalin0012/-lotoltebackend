package com.loto.lte.web.dao;

import com.loto.lte.core.dto.JsonObject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Base64WriteImageDao {
    int save(JsonObject jsonObject);
    int delete(JsonObject jsonObject);
    int update(JsonObject jsonObject);
    String getResourcesImageById(JsonObject jsonObject);
    int count();
}
