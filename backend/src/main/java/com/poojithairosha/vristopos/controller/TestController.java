package com.poojithairosha.vristopos.controller;

import com.poojithairosha.vristopos.model.product.Product;
import com.poojithairosha.vristopos.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping
    public String test() {
        return "This is a test endpoint";
    }

}
