package com.poojithairosha.vristopos.service;

import com.poojithairosha.vristopos.dto.StockSearchDTO;
import com.poojithairosha.vristopos.dto.StockUpdateDTO;
import com.poojithairosha.vristopos.model.product.Stock;
import org.springframework.data.domain.Page;

import java.io.ByteArrayInputStream;

public interface StockService {

    Page<Stock> searchStock(StockSearchDTO stockSearchDTO, int page, int size);

    Stock updatePrice(StockUpdateDTO stockUpdateDTO);

    ByteArrayInputStream getStockReport();

}
