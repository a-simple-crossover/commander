package com.master.commander.commodity.repository;

import com.master.commander.commodity.entity.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommodityRepository extends JpaRepository<Commodity, Long> {
    Optional<Commodity> findBySkuCode(String skuCode);
    
    List<Commodity> findByCategory(String category);
    
    List<Commodity> findByNameContainingIgnoreCase(String name);
    
    List<Commodity> findByBrand(String brand);
    
    boolean existsBySkuCode(String skuCode);
    
    List<Commodity> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    @Query("SELECT c FROM Commodity c WHERE c.stockQuantity <= c.minStockLevel AND c.active = true")
    List<Commodity> findLowStockCommodities();
    
    List<Commodity> findByActiveTrue();
} 