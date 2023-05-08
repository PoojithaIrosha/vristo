package com.poojithairosha.vristopos.service;

import com.poojithairosha.vristopos.dto.InvoiceDTO;
import com.poojithairosha.vristopos.dto.InvoiceReport;
import com.poojithairosha.vristopos.dto.SearchGrnInvoiceDTO;
import com.poojithairosha.vristopos.dto.UserDTO;
import com.poojithairosha.vristopos.model.invoice.Invoice;
import com.poojithairosha.vristopos.model.invoice.InvoiceItem;
import com.poojithairosha.vristopos.model.invoice.InvoicePayment;
import com.poojithairosha.vristopos.model.invoice.InvoiceResponseDTO;
import com.poojithairosha.vristopos.model.product.Product;
import com.poojithairosha.vristopos.model.product.Stock;
import com.poojithairosha.vristopos.model.user.User;
import com.poojithairosha.vristopos.repository.InvoiceRepository;
import com.poojithairosha.vristopos.repository.StockRepository;
import com.poojithairosha.vristopos.repository.UserRepository;
import jakarta.activation.DataSource;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceService {

    private final UserRepository userRepository;
    private final StockRepository stockRepository;
    private final InvoiceRepository invoiceRepository;
    private final JavaMailSender javaMailSender;

    public ByteArrayInputStream addInvoice(InvoiceDTO invoiceDTO) {

        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new RuntimeException("User not found"));

        Invoice invoice = Invoice.builder()
                .dateTime(LocalDateTime.now())
                .user(user)
                .build();

        InvoicePayment invoicePayment = InvoicePayment.builder()
                .invoice(invoice)
                .payment(invoiceDTO.invoicePayment().getPayment())
                .balance(invoiceDTO.invoicePayment().getBalance())
                .paymentType(invoiceDTO.invoicePayment().getPaymentType())
                .build();
        invoice.setInvoicePayment(invoicePayment);

        List<InvoiceItem> invoiceItemList = new ArrayList<>();

        invoiceDTO.invoiceItems().forEach(
                item -> {
                    Stock stock = stockRepository.findById(item.stock().getId()).orElseThrow(() -> new RuntimeException("Stock not found"));

                    stock.setQuantity(stock.getQuantity() - item.quantity());

                    InvoiceItem invoiceItem = InvoiceItem.builder()
                            .invoice(invoice)
                            .stock(stock)
                            .quantity(item.quantity())
                            .build();

                    invoiceItemList.add(invoiceItem);
                }
        );

        invoice.setInvoiceItems(invoiceItemList);
        invoiceRepository.save(invoice);
        log.info("Invoice saved successfully");

        List<InvoiceReport> invoiceReportList = new ArrayList<>();

        AtomicInteger count = new AtomicInteger(1);
        invoiceItemList.forEach(item -> {
            invoiceReportList.add(
                    InvoiceReport.builder()
                            .itemCode(String.valueOf(count.getAndIncrement()))
                            .productName(item.getStock().getProduct().getName())
                            .price(item.getStock().getSellingPrice().toString())
                            .quantity(String.valueOf(item.getQuantity()))
                            .total(String.valueOf(item.getStock().getSellingPrice() * item.getQuantity()))
                            .build()
            );
        });

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            InputStream inputStream = this.getClass().getResourceAsStream("/reports/invoice_report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("invoiceId", invoice.getId().toString());
            parameters.put("dateTime", invoice.getDateTime().toLocalDate().toString());
            parameters.put("total", String.valueOf(invoiceDTO.invoicePayment().getPayment() - invoiceDTO.invoicePayment().getBalance()));
            parameters.put("payment", invoiceDTO.invoicePayment().getPayment().toString());
            parameters.put("balance", invoiceDTO.invoicePayment().getBalance().toString());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(invoiceReportList));
            JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
            DataSource dataSource = new ByteArrayDataSource(baos.toByteArray(), "application/pdf");
            log.info("Invoice generated successfully");

        } catch (JRException ex) {
            throw new RuntimeException(ex);
        }

        log.info("Invoice sent successfully");
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public Page<InvoiceResponseDTO> getAllInvoices(SearchGrnInvoiceDTO searchGrnInvoiceDTO) {
        log.info("Getting all invoices");
        PageRequest pageRequest = PageRequest.of(searchGrnInvoiceDTO.page(), searchGrnInvoiceDTO.size(), Sort.by("dateTime").descending());
        if (searchGrnInvoiceDTO.startDate() == null && searchGrnInvoiceDTO.endDate() == null) {
            return invoiceRepository.findAll(pageRequest).map(invoice -> InvoiceResponseDTO.builder()
                    .id(invoice.getId())
                    .dateTime(invoice.getDateTime())
                    .user(UserDTO.builder()
                            .id(invoice.getUser().getId())
                            .email(invoice.getUser().getEmail())
                            .name(invoice.getUser().getName())
                            .mobile(invoice.getUser().getMobile())
                            .createdAt(invoice.getUser().getCreatedAt())
                            .role(invoice.getUser().getRole())
                            .isEnabled(invoice.getUser().getIsEnabled())
                            .build()
                    )
                    .invoicePayment(invoice.getInvoicePayment())
                    .invoiceItems(invoice.getInvoiceItems())
                    .build());
        } else if (searchGrnInvoiceDTO.startDate() != null && searchGrnInvoiceDTO.endDate() == null) {
            return invoiceRepository.findAllByDateTimeBetween(searchGrnInvoiceDTO.startDate(), LocalDateTime.now(), pageRequest).map(invoice -> InvoiceResponseDTO.builder()
                    .id(invoice.getId())
                    .dateTime(invoice.getDateTime())
                    .user(UserDTO.builder()
                            .id(invoice.getUser().getId())
                            .email(invoice.getUser().getEmail())
                            .name(invoice.getUser().getName())
                            .mobile(invoice.getUser().getMobile())
                            .createdAt(invoice.getUser().getCreatedAt())
                            .role(invoice.getUser().getRole())
                            .isEnabled(invoice.getUser().getIsEnabled())
                            .build()
                    )
                    .invoicePayment(invoice.getInvoicePayment())
                    .invoiceItems(invoice.getInvoiceItems())
                    .build());
        } else if (searchGrnInvoiceDTO.startDate() == null && searchGrnInvoiceDTO.endDate() != null) {
            return invoiceRepository.findAllByDateTimeBetween(LocalDateTime.now().minusDays(30), searchGrnInvoiceDTO.endDate(), pageRequest).map(invoice -> InvoiceResponseDTO.builder()
                    .id(invoice.getId())
                    .dateTime(invoice.getDateTime())
                    .user(UserDTO.builder()
                            .id(invoice.getUser().getId())
                            .email(invoice.getUser().getEmail())
                            .name(invoice.getUser().getName())
                            .mobile(invoice.getUser().getMobile())
                            .createdAt(invoice.getUser().getCreatedAt())
                            .role(invoice.getUser().getRole())
                            .isEnabled(invoice.getUser().getIsEnabled())
                            .build()
                    )
                    .invoicePayment(invoice.getInvoicePayment())
                    .invoiceItems(invoice.getInvoiceItems())
                    .build());
        } else {
            return invoiceRepository.findAllByDateTimeBetween(searchGrnInvoiceDTO.startDate(), searchGrnInvoiceDTO.endDate(), pageRequest).map(invoice -> InvoiceResponseDTO.builder()
                    .id(invoice.getId())
                    .dateTime(invoice.getDateTime())
                    .user(UserDTO.builder()
                            .id(invoice.getUser().getId())
                            .email(invoice.getUser().getEmail())
                            .name(invoice.getUser().getName())
                            .mobile(invoice.getUser().getMobile())
                            .createdAt(invoice.getUser().getCreatedAt())
                            .role(invoice.getUser().getRole())
                            .isEnabled(invoice.getUser().getIsEnabled())
                            .build()
                    )
                    .invoicePayment(invoice.getInvoicePayment())
                    .invoiceItems(invoice.getInvoiceItems())
                    .build());
        }
    }

    public ByteArrayInputStream generateReport(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Invoice not found"));

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream inputStream = this.getClass().getResourceAsStream("/reports/invoice_report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("invoiceId", invoice.getId().toString());
            parameters.put("dateTime", invoice.getDateTime().toLocalDate().toString());
            parameters.put("total", String.valueOf(invoice.getInvoicePayment().getPayment() - invoice.getInvoicePayment().getBalance()));
            parameters.put("payment", invoice.getInvoicePayment().getPayment().toString());
            parameters.put("balance", invoice.getInvoicePayment().getBalance().toString());

            List<InvoiceReport> invoiceReportList = new ArrayList<>();
            AtomicInteger count = new AtomicInteger(1);

            invoice.getInvoiceItems().forEach(item -> {
                invoiceReportList.add(
                        InvoiceReport.builder()
                                .itemCode(String.valueOf(count.getAndIncrement()))
                                .productName(item.getStock().getProduct().getName())
                                .price(item.getStock().getSellingPrice().toString())
                                .quantity(String.valueOf(item.getQuantity()))
                                .total(String.valueOf(item.getStock().getSellingPrice() * item.getQuantity()))
                                .build()
                );
            });

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(invoiceReportList));
            JasperExportManager.exportReportToPdfStream(jasperPrint, baos);

            log.info("Invoice generated successfully for invoice id: {}", id);
            return new ByteArrayInputStream(baos.toByteArray());
        } catch (JRException ex) {
            throw new RuntimeException(ex);
        }

    }

    public List<Product> getTopSellingProducts() {
        return invoiceRepository.findTopSellingProducts();
    }
}
