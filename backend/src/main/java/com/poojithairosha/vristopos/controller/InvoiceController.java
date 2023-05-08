package com.poojithairosha.vristopos.controller;

import com.poojithairosha.vristopos.dto.InvoiceDTO;
import com.poojithairosha.vristopos.dto.SearchGrnInvoiceDTO;
import com.poojithairosha.vristopos.model.invoice.InvoiceResponseDTO;
import com.poojithairosha.vristopos.model.product.Product;
import com.poojithairosha.vristopos.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("api/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<InputStreamResource> addInvoice(@Valid @RequestBody InvoiceDTO invoiceDTO) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "inline; filename=grn_report.pdf");
        ByteArrayInputStream byteArrayInputStream = invoiceService.addInvoice(invoiceDTO);
        return ResponseEntity.ok().headers(httpHeaders).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(new InputStreamResource(byteArrayInputStream));
    }

    @PostMapping("/search")
    public ResponseEntity<Page<InvoiceResponseDTO>> getAllInvoices(@RequestBody SearchGrnInvoiceDTO searchGrnInvoiceDTO) {
        return ResponseEntity.ok(invoiceService.getAllInvoices(searchGrnInvoiceDTO));
    }

    @GetMapping("/report/{id}")
    public ResponseEntity<InputStreamResource> getInvoiceReport(@PathVariable Long id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "inline; filename=grn_report.pdf");
        ByteArrayInputStream byteArrayInputStream = invoiceService.generateReport(id);
        return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping("/top-selling")
    public ResponseEntity<List<Product>> getTopSellingProducts() {
        return ResponseEntity.ok(invoiceService.getTopSellingProducts());
    }

}
