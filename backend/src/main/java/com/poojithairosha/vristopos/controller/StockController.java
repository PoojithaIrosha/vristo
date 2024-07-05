package com.poojithairosha.vristopos.controller;

import com.poojithairosha.vristopos.dto.StockSearchParentDTO;
import com.poojithairosha.vristopos.dto.StockUpdateDTO;
import com.poojithairosha.vristopos.model.product.Stock;
import com.poojithairosha.vristopos.service.StockService;
import com.poojithairosha.vristopos.service.impl.StockServiceImpl;
import com.poojithairosha.vristopos.util.UriProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping(UriProperties.URI_STOCK)
@RequiredArgsConstructor
@Slf4j
public class StockController {

    private final StockService service;

    @GetMapping(UriProperties.URI_STOCK_REPORT)
    public ResponseEntity<InputStreamResource> getStockReport() {
        log.info("Start execute getStockReport");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "inline; filename=grn_report.pdf");
        ByteArrayInputStream in = service.getStockReport();
        log.info("Stock report generated successfully");
        return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(in));
    }

    @PostMapping(UriProperties.URI_SEARCH)
    public Page<Stock> searchStock(@RequestBody StockSearchParentDTO stockSearchParentDTO) {
        log.info("Start execute searchStock");
        return service.searchStock(stockSearchParentDTO.stock(), stockSearchParentDTO.page(), stockSearchParentDTO.size());
    }

    @PostMapping(UriProperties.URI_STOCK_UPDATE_PRICE)
    public Stock updatePrice(@RequestBody StockUpdateDTO stockUpdateDTO) {
        log.info("Start execute update price");
        return service.updatePrice(stockUpdateDTO);
    }
}
