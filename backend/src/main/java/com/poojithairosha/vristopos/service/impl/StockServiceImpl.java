package com.poojithairosha.vristopos.service.impl;

import com.poojithairosha.vristopos.dto.StockReportDTO;
import com.poojithairosha.vristopos.dto.StockSearchDTO;
import com.poojithairosha.vristopos.dto.StockUpdateDTO;
import com.poojithairosha.vristopos.model.product.Stock;
import com.poojithairosha.vristopos.repository.StockRepository;
import com.poojithairosha.vristopos.repository.StockSearchDao;
import com.poojithairosha.vristopos.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockServiceImpl implements StockService {

    private final StockSearchDao stockSearchDao;
    private final StockRepository stockRepository;

    @Override
    public Page<Stock> searchStock(StockSearchDTO stockSearchDTO, int page, int size) {
        log.info("Start executing search stock");
        Page<Stock> stocks = stockSearchDao.searchStock(stockSearchDTO, page, size);
        log.info("Finished executing search stock: {}", stocks.getTotalElements());
        return stocks;
    }

    @Override
    public Stock updatePrice(StockUpdateDTO stockUpdateDTO) {
        log.info("Start executing updatePrice");
        if (stockRepository.existsById(stockUpdateDTO.stockId())) {
            Stock stock = stockRepository.findById(stockUpdateDTO.stockId()).get();
            stock.setSellingPrice(stockUpdateDTO.newPrice());
            Stock ns = stockRepository.save(stock);
            log.info("Stock updated successfully with id: {}", stockUpdateDTO.stockId());
            return ns;
        } else {
            throw new RuntimeException("Stock not found");
        }
    }

    @Override
    public ByteArrayInputStream getStockReport() {
        log.info("Start executing getStockReport");
        List<Stock> stockList = stockRepository.findAllByQuantityGreaterThan(0);

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream inputStream = this.getClass().getResourceAsStream("/reports/stock_report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            log.info("Jasper report compiled successfully");

            Map<String, Object> parameters = new HashMap<>();

            List<StockReportDTO> stockReportDTOList = new ArrayList<>();

            DecimalFormat twoDForm = new DecimalFormat("#.##");
            stockList.stream().map(stock -> StockReportDTO.builder()
                    .stockId(stock.getId().toString())
                    .name(stock.getProduct().getName())
                    .brand(stock.getProduct().getBrand().getName())
                    .price("Rs." + twoDForm.format(stock.getSellingPrice()))
                    .qty(stock.getQuantity().toString())
                    .exd(stock.getExpireDate().toString())
                    .build()).forEach(stockReportDTOList::add);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(stockReportDTOList));
            JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
            log.info("Finished generating stock report");
            return new ByteArrayInputStream(baos.toByteArray());
        } catch (JRException e) {
            log.error("Error occurred while generating stock report", e);
            throw new RuntimeException(e.getMessage());
        }

    }
}
