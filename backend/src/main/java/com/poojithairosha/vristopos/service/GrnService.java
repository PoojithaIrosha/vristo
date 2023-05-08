package com.poojithairosha.vristopos.service;

import com.poojithairosha.vristopos.dto.*;
import com.poojithairosha.vristopos.model.grn.Grn;
import com.poojithairosha.vristopos.model.grn.GrnItem;
import com.poojithairosha.vristopos.model.grn.GrnPayment;
import com.poojithairosha.vristopos.model.product.Stock;
import com.poojithairosha.vristopos.model.user.User;
import com.poojithairosha.vristopos.repository.GrnRepository;
import com.poojithairosha.vristopos.repository.StockRepository;
import com.poojithairosha.vristopos.repository.SupplierRepository;
import com.poojithairosha.vristopos.repository.UserRepository;
import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
public class GrnService {

    private final GrnRepository grnRepository;
    private final StockRepository stockRepository;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final SupplierRepository supplierRepository;

    @Transactional
    public ByteArrayInputStream addGrn(GrnDTO grnDTO) {

        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new RuntimeException("User not found"));

        Grn grn = Grn.builder().dateTime(LocalDateTime.now()).supplier(grnDTO.supplier()).user(user).build();

        GrnPayment grnPayment = GrnPayment.builder().grn(grn).payment(grnDTO.grnPayment().getPayment()).balance(grnDTO.grnPayment().getBalance()).paymentType(grnDTO.grnPayment().getPaymentType()).build();
        grn.setGrnPayment(grnPayment);

        List<GrnItem> grnItemList = new ArrayList<>();

        grnDTO.grnItems().forEach(
                grnItem -> {
                    Stock stock;
                    if (stockRepository.existsByProductAndManufactureDateAndExpireDateAndSellingPrice(
                            grnItem.product(),
                            grnItem.manufactureDate(),
                            grnItem.expireDate(),
                            grnItem.sellingPrice()
                    )) {

                        stock = stockRepository.findByProductAndManufactureDateAndExpireDateAndSellingPrice(grnItem.product(),
                                grnItem.manufactureDate(),
                                grnItem.expireDate(),
                                grnItem.sellingPrice()).orElseThrow(() -> new RuntimeException("Stock not found"));

                        stock.setQuantity(stock.getQuantity() + grnItem.quantity());
                    } else {
                        stock = Stock.builder()
                                .product(grnItem.product())
                                .manufactureDate(grnItem.manufactureDate())
                                .expireDate(grnItem.expireDate())
                                .sellingPrice(grnItem.sellingPrice())
                                .quantity(grnItem.quantity())
                                .build();
                    }

                    GrnItem newGrnItem = GrnItem.builder()
                            .quantity(grnItem.quantity())
                            .buyingPrice(grnItem.buyingPrice())
                            .stock(stock)
                            .grn(grn)
                            .build();

                    grnItemList.add(newGrnItem);
                }
        );

        grn.setGrnItems(grnItemList);
        grnRepository.save(grn);
        log.info("Grn saved successfully");

        List<GRNReport> grnReportList = new ArrayList<>();

        AtomicInteger count = new AtomicInteger(1);
        grnItemList.forEach(item -> {
            grnReportList.add(GRNReport.builder().id(count.getAndIncrement()).productName(item.getStock().getProduct().getName()).quantity(item.getQuantity()).price(String.valueOf(item.getBuyingPrice())).total(String.valueOf(item.getQuantity() * item.getBuyingPrice())).build());
        });

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // Compile jr xml file.
            InputStream inputStream = this.getClass().getResourceAsStream("/reports/grn_report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("grnId", grn.getId().toString());
            parameters.put("supplierName", grn.getSupplier().getName());
            parameters.put("supplierEmail", grn.getSupplier().getEmail());
            parameters.put("companyName", grn.getSupplier().getCompany().getName());
            parameters.put("companyAddress", grn.getSupplier().getCompany().getAddress());
            parameters.put("grandTotal", String.valueOf(grnDTO.grnPayment().getPayment() - grnDTO.grnPayment().getBalance()));
            parameters.put("payment", grnDTO.grnPayment().getPayment().toString());
            parameters.put("balance", grnDTO.grnPayment().getBalance().toString());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(grnReportList));
            JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
            DataSource dataSource = new ByteArrayDataSource(baos.toByteArray(), "application/pdf");

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject("Vristo - GRN Invoice: GRN #" + grn.getId());
            mimeMessageHelper.setText("New grn added to the system. Please find the attached invoice.");
            mimeMessageHelper.setTo(InternetAddress.parse(grn.getSupplier().getEmail(), true));
            mimeMessageHelper.setFrom("poojithairosha9311@gmail.com");
            mimeMessageHelper.addAttachment("GRN Invoice #" + grn.getId() + ".pdf", dataSource);
            javaMailSender.send(mimeMessage);
            log.info("Email sent successfully");
        } catch (MailSendException | MessagingException ex) {
            throw new RuntimeException("Email sending failed");
        } catch (JRException ex) {
            throw new RuntimeException(ex);
        }
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public Page<GrnResponseDTO> getAllGrns(SearchGrnInvoiceDTO searchGrnInvoiceDTO) {
        PageRequest pageRequest = PageRequest.of(searchGrnInvoiceDTO.page(), searchGrnInvoiceDTO.size(), Sort.by("dateTime").descending());

        if (searchGrnInvoiceDTO.startDate() == null && searchGrnInvoiceDTO.endDate() == null) {
            return grnRepository.findAll(pageRequest).map(grn -> GrnResponseDTO.builder()
                    .id(grn.getId())
                    .dateTime(grn.getDateTime())
                    .supplier(grn.getSupplier())
                    .user(UserDTO.builder()
                            .id(grn.getUser().getId())
                            .email(grn.getUser().getEmail())
                            .name(grn.getUser().getName())
                            .mobile(grn.getUser().getMobile())
                            .createdAt(grn.getUser().getCreatedAt())
                            .role(grn.getUser().getRole())
                            .isEnabled(grn.getUser().getIsEnabled())
                            .build()
                    )
                    .grnItems(grn.getGrnItems())
                    .grnPayment(grn.getGrnPayment())
                    .build()
            );
        } else if (searchGrnInvoiceDTO.startDate() != null && searchGrnInvoiceDTO.endDate() == null) {
            return grnRepository.findAllByDateTimeBetween(searchGrnInvoiceDTO.startDate(), LocalDateTime.now(), pageRequest).map(grn -> GrnResponseDTO.builder()
                    .id(grn.getId())
                    .dateTime(grn.getDateTime())
                    .supplier(grn.getSupplier())
                    .user(UserDTO.builder()
                            .id(grn.getUser().getId())
                            .email(grn.getUser().getEmail())
                            .name(grn.getUser().getName())
                            .mobile(grn.getUser().getMobile())
                            .createdAt(grn.getUser().getCreatedAt())
                            .role(grn.getUser().getRole())
                            .isEnabled(grn.getUser().getIsEnabled())
                            .build()
                    )
                    .grnItems(grn.getGrnItems())
                    .grnPayment(grn.getGrnPayment())
                    .build()
            );
        } else if (searchGrnInvoiceDTO.startDate() == null && searchGrnInvoiceDTO.endDate() != null) {
            return grnRepository.findAllByDateTimeBetween(LocalDateTime.now().minusDays(30), searchGrnInvoiceDTO.endDate(), pageRequest).map(grn -> GrnResponseDTO.builder()
                    .id(grn.getId())
                    .dateTime(grn.getDateTime())
                    .supplier(grn.getSupplier())
                    .user(UserDTO.builder()
                            .id(grn.getUser().getId())
                            .email(grn.getUser().getEmail())
                            .name(grn.getUser().getName())
                            .mobile(grn.getUser().getMobile())
                            .createdAt(grn.getUser().getCreatedAt())
                            .role(grn.getUser().getRole())
                            .isEnabled(grn.getUser().getIsEnabled())
                            .build()
                    )
                    .grnItems(grn.getGrnItems())
                    .grnPayment(grn.getGrnPayment())
                    .build()
            );
        } else {
            return grnRepository.findAllByDateTimeBetween(searchGrnInvoiceDTO.startDate(), searchGrnInvoiceDTO.endDate(), pageRequest).map(grn -> GrnResponseDTO.builder()
                    .id(grn.getId())
                    .dateTime(grn.getDateTime())
                    .supplier(grn.getSupplier())
                    .user(UserDTO.builder()
                            .id(grn.getUser().getId())
                            .email(grn.getUser().getEmail())
                            .name(grn.getUser().getName())
                            .mobile(grn.getUser().getMobile())
                            .createdAt(grn.getUser().getCreatedAt())
                            .role(grn.getUser().getRole())
                            .isEnabled(grn.getUser().getIsEnabled())
                            .build()
                    )
                    .grnItems(grn.getGrnItems())
                    .grnPayment(grn.getGrnPayment())
                    .build()
            );
        }
    }

    public ByteArrayInputStream generateReport(Long id) {

        Grn grn = grnRepository.findById(id).orElseThrow(() -> new RuntimeException("Grn not found"));

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream inputStream = this.getClass().getResourceAsStream("/reports/grn_report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("grnId", grn.getId().toString());
            parameters.put("supplierName", grn.getSupplier().getName());
            parameters.put("supplierEmail", grn.getSupplier().getEmail());
            parameters.put("companyName", grn.getSupplier().getCompany().getName());
            parameters.put("companyAddress", grn.getSupplier().getCompany().getAddress());
            parameters.put("grandTotal", String.valueOf(grn.getGrnPayment().getPayment() - grn.getGrnPayment().getBalance()));
            parameters.put("payment", grn.getGrnPayment().getPayment().toString());
            parameters.put("balance", grn.getGrnPayment().getBalance().toString());

            List<GRNReport> grnReportList = new ArrayList<>();
            AtomicInteger count = new AtomicInteger(1);

            grn.getGrnItems().forEach(item -> {
                grnReportList.add(GRNReport.builder().id(count.getAndIncrement()).productName(item.getStock().getProduct().getName()).quantity(item.getQuantity()).price(String.valueOf(item.getBuyingPrice())).total(String.valueOf(item.getQuantity() * item.getBuyingPrice())).build());
            });

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(grnReportList));
            JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
            return new ByteArrayInputStream(baos.toByteArray());
        } catch (JRException ex) {
            throw new RuntimeException(ex);
        }
    }
}
