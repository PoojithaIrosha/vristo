package com.poojithairosha.vristopos.controller;

import com.poojithairosha.vristopos.dto.StockUpdateDTO;
import com.poojithairosha.vristopos.model.product.Stock;
import com.poojithairosha.vristopos.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("api/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService service;

    @GetMapping("/report")
    public ResponseEntity<InputStreamResource> getStockReport() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "inline; filename=grn_report.pdf");
        ByteArrayInputStream in = service.getStockReport();
        return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(in));
    }

    @PostMapping("/search")
    public Page<Stock> searchStock(@RequestBody StockSearchParentDTO stockSearchParentDTO) {
        return service.searchStock(stockSearchParentDTO.stock(), stockSearchParentDTO.page(), stockSearchParentDTO.size());
    }

    @PostMapping("/update-price")
    public Stock updatePrice(@RequestBody StockUpdateDTO stockUpdateDTO) {
        return service.updatePrice(stockUpdateDTO);
    }
}
