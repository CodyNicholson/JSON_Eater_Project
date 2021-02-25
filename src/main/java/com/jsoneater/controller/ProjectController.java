package com.jsoneater.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsoneater.constant.Constant;
import com.jsoneater.error.restCustomExceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin
public class ProjectController {
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(value = "/eat")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> eatJson(
            @RequestBody String body
    ) {
        HashMap genericJson;

        try {
            genericJson = objectMapper.readValue(body, HashMap.class);
        } catch (Exception ex) {
            throw new BadRequestException(Constant.INVALID_JSON, ex);
        }

        return new ResponseEntity<>(genericJson, HttpStatus.OK);
    }
}
