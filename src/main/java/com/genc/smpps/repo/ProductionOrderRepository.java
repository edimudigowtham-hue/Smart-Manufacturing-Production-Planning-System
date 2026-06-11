package com.genc.smpps.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.genc.smpps.model.ProductionOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionOrderRepository extends JpaRepository<ProductionOrder, Integer> {
}