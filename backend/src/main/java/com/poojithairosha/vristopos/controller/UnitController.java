package com.poojithairosha.vristopos.controller;

import com.poojithairosha.vristopos.model.product.Unit;
import com.poojithairosha.vristopos.service.UnitService;
import com.poojithairosha.vristopos.service.impl.UnitServiceImpl;
import com.poojithairosha.vristopos.util.UriProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(UriProperties.URI_UNITS)
@RequiredArgsConstructor
@Slf4j
public class UnitController {

    private final UnitService unitService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Unit>> getUnits() {
        log.info("Start execute getUnits");
        return ResponseEntity.ok(unitService.getUnits());
    }

}
