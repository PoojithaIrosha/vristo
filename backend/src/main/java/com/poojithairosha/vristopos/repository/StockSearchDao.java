package com.poojithairosha.vristopos.repository;

import com.poojithairosha.vristopos.dto.StockSearchDTO;
import com.poojithairosha.vristopos.model.product.Stock;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StockSearchDao {

    private final EntityManager entityManager;

    public Page<Stock> searchStock(StockSearchDTO stockSearchDTO, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Stock> stockCriteriaQuery = criteriaBuilder.createQuery(Stock.class);
        List<Predicate> predicates = new ArrayList<>();

        Root<Stock> stockRoot = stockCriteriaQuery.from(Stock.class);

        if (stockSearchDTO.productName() != null) {
            Predicate productNamePredicate = criteriaBuilder.like(stockRoot.get("product").get("name"), "%" + stockSearchDTO.productName() + "%");
            predicates.add(productNamePredicate);
        }

        if (stockSearchDTO.brandId() != null) {
            Predicate brandIdPredicate = criteriaBuilder.equal(stockRoot.get("product").get("brand").get("id"), stockSearchDTO.brandId());
            predicates.add(brandIdPredicate);
        }

        if (stockSearchDTO.categoryId() != null) {
            Predicate categoryIdPredicate = criteriaBuilder.equal(stockRoot.get("product").get("brand").get("category").get("id"), stockSearchDTO.categoryId());
            predicates.add(categoryIdPredicate);
        }

        if (stockSearchDTO.sellingPriceFrom() != null) {
            Predicate sellingPricePredicate;
            if (stockSearchDTO.sellingPriceTo() != null) {
                sellingPricePredicate = criteriaBuilder.between(stockRoot.get("sellingPrice"), stockSearchDTO.sellingPriceFrom(), stockSearchDTO.sellingPriceTo());
            } else {
                sellingPricePredicate = criteriaBuilder.greaterThanOrEqualTo(stockRoot.get("sellingPrice"), stockSearchDTO.sellingPriceFrom());
            }
            predicates.add(sellingPricePredicate);
        } else if (stockSearchDTO.sellingPriceTo() != null) {
            Predicate sellingPricePredicate = criteriaBuilder.lessThanOrEqualTo(stockRoot.get("sellingPrice"), stockSearchDTO.sellingPriceTo());
            predicates.add(sellingPricePredicate);
        }

        if (stockSearchDTO.manufactureDateFrom() != null) {
            Predicate manufactureDatePredicate;
            if (stockSearchDTO.manufactureDateTo() != null) {
                manufactureDatePredicate = criteriaBuilder.between(stockRoot.get("manufactureDate"), stockSearchDTO.manufactureDateFrom(), stockSearchDTO.manufactureDateTo());
            } else {
                manufactureDatePredicate = criteriaBuilder.greaterThanOrEqualTo(stockRoot.get("manufactureDate"), stockSearchDTO.manufactureDateFrom());
            }
            predicates.add(manufactureDatePredicate);
        } else if (stockSearchDTO.manufactureDateTo() != null) {
            Predicate manufactureDatePredicate = criteriaBuilder.lessThanOrEqualTo(stockRoot.get("manufactureDate"), stockSearchDTO.manufactureDateTo());
            predicates.add(manufactureDatePredicate);
        }

        if (stockSearchDTO.expireDateFrom() != null) {
            Predicate expireDatePredicate;
            if (stockSearchDTO.expireDateTo() != null) {
                expireDatePredicate = criteriaBuilder.between(stockRoot.get("expireDate"), stockSearchDTO.expireDateFrom(), stockSearchDTO.expireDateTo());
            } else {
                expireDatePredicate = criteriaBuilder.greaterThanOrEqualTo(stockRoot.get("expireDate"), stockSearchDTO.expireDateFrom());
            }
            predicates.add(expireDatePredicate);
        } else if (stockSearchDTO.expireDateTo() != null) {
            Predicate expireDatePredicate = criteriaBuilder.lessThanOrEqualTo(stockRoot.get("expireDate"), stockSearchDTO.expireDateTo());
            predicates.add(expireDatePredicate);
        }

        Predicate orPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        stockCriteriaQuery.where(orPredicate);

        TypedQuery<Stock> resultList1 = entityManager.createQuery(stockCriteriaQuery).setFirstResult((int) pageRequest.getOffset()).setMaxResults(pageRequest.getPageSize());

        return new PageImpl<>(resultList1.getResultList(), pageRequest, entityManager.createQuery(stockCriteriaQuery).getResultList().size());
    }
}
