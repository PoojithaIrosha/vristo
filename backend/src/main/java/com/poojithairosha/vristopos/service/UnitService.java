package com.poojithairosha.vristopos.service;

import com.poojithairosha.vristopos.model.product.Unit;
import com.poojithairosha.vristopos.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;

    public List<Unit> getUnits() {
        return unitRepository.findAll();
    }
}
