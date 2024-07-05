package com.poojithairosha.vristopos.service.impl;

import com.poojithairosha.vristopos.model.product.Unit;
import com.poojithairosha.vristopos.repository.UnitRepository;
import com.poojithairosha.vristopos.service.UnitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;

    @Override
    public List<Unit> getUnits() {
        log.info("Start searching for units");
        List<Unit> list = unitRepository.findAll();
        log.info("Found {} units", list.size());
        return list;
    }
}
