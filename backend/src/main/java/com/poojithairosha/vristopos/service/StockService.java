package com.poojithairosha.vristopos.service;

import com.poojithairosha.vristopos.dto.StockReportDTO;
import com.poojithairosha.vristopos.dto.StockSearchDTO;
import com.poojithairosha.vristopos.dto.StockUpdateDTO;
import com.poojithairosha.vristopos.model.product.Stock;
import com.poojithairosha.vristopos.repository.StockRepository;
import com.poojithairosha.vristopos.repository.StockSearchDao;
import lombok.RequiredArgsConstructor;
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
public class StockService {

    private final StockSearchDao stockSearchDao;
    private final StockRepository stockRepository;


    public Page<Stock> searchStock(StockSearchDTO stockSearchDTO, int page, int size) {
        return stockSearchDao.searchStock(stockSearchDTO, page, size);
    }

    public Stock updatePrice(StockUpdateDTO stockUpdateDTO) {
        if (stockRepository.existsById(stockUpdateDTO.stockId())) {
            Stock stock = stockRepository.findById(stockUpdateDTO.stockId()).get();
            stock.setSellingPrice(stockUpdateDTO.newPrice());
            return stockRepository.save(stock);
        } else {
            throw new RuntimeException("Stock not found");
        }
    }

    public ByteArrayInputStream getStockReport() {
        List<Stock> stockList = stockRepository.findAllByQuantityGreaterThan(0);

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream inputStream = this.getClass().getResourceAsStream("/reports/stock_report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

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
            return new ByteArrayInputStream(baos.toByteArray());
        } catch (JRException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
