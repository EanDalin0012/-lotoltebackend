package com.loto.lte.web.dao;

import com.loto.lte.core.dto.JsonObject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IdentifyDao {
    int save(JsonObject jsonObject);
    int count();
}
