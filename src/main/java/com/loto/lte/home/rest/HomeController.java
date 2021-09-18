package com.loto.lte.home.rest;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/unsecure/home")
public class HomeController {
    @PostMapping(value = "/post")
    public String index(@RequestBody JsonNode jsonNode) throws Exception {
//        try {
//            JsonNode name = jsonNode.get("name");
//            if (name == null) {
//                throw  new Exception("Invalid Name");
//            }
//        }catch (Exception e ) {
//            throw e;
//        }
        return "Hell";
    }
}
