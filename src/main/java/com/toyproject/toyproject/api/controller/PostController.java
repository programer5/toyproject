package com.toyproject.toyproject.api.controller;

import com.toyproject.toyproject.api.request.PostCreate;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class PostController {

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostCreate params, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError fieldError = fieldErrors.get(0);
            String field = fieldError.getField();
            String defaultMessage = fieldError.getDefaultMessage();

            Map<String, String> error = new HashMap<>();
            error.put(field, defaultMessage);
            return error;
        }

        return Map.of();
    }
}
