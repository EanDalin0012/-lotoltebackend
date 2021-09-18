package com.loto.lte.core.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loto.lte.core.dto.JsonObject;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/client/v0")
public class ClientCloseApplicationRest {
    static Logger log = Logger.getLogger(ClientCloseApplicationRest.class.getName());

    @PostMapping(value = "/close")
    public void clientCloseApplication(@RequestBody JsonObject jsonObject, @RequestParam("lang") String lang) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info("clientCloseApplication:"+objectMapper.writeValueAsString(jsonObject));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
