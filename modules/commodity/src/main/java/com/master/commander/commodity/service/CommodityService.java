package com.master.commander.commodity.service;

import com.master.commander.commodity.bo.CommodityBO;
import com.master.commander.commodity.converter.CommodityConverter;
import com.master.commander.commodity.entity.Commodity;
import com.master.commander.commodity.repository.CommodityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommodityService {
    private final CommodityRepository commodityRepository;
    private final CommodityConverter commodityConverter;

    @Transactional(readOnly = true)
    public List<CommodityBO> findAll() {
        return commodityConverter.toBOList(commodityRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<CommodityBO> findAllActive() {
        return commodityConverter.toBOList(commodityRepository.findByActiveTrue());
    }

    @Transactional(readOnly = true)
    public CommodityBO findById(Long id) {
        return commodityConverter.toBO(commodityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Commodity not found with id: " + id)));
    }

    @Transactional(readOnly = true)
    public CommodityBO findBySkuCode(String skuCode) {
        return commodityConverter.toBO(commodityRepository.findBySkuCode(skuCode)
                .orElseThrow(() -> new EntityNotFoundException("Commodity not found with SKU: " + skuCode)));
    }

    @Transactional(readOnly = true)
    public List<CommodityBO> findByCategory(String category) {
        return commodityConverter.toBOList(commodityRepository.findByCategory(category));
    }

    @Transactional(readOnly = true)
    public List<CommodityBO> findByBrand(String brand) {
        return commodityConverter.toBOList(commodityRepository.findByBrand(brand));
    }

    @Transactional(readOnly = true)
    public List<CommodityBO> searchByName(String name) {
        return commodityConverter.toBOList(commodityRepository.findByNameContainingIgnoreCase(name));
    }

    @Transactional(readOnly = true)
    public List<CommodityBO> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return commodityConverter.toBOList(commodityRepository.findByPriceBetween(minPrice, maxPrice));
    }

    @Transactional(readOnly = true)
    public List<CommodityBO> findLowStockCommodities() {
        return commodityConverter.toBOList(commodityRepository.findLowStockCommodities());
    }

    @Transactional
    public CommodityBO create(CommodityBO commodityBO) {
        if (commodityBO.getSkuCode() != null && 
            commodityRepository.existsBySkuCode(commodityBO.getSkuCode())) {
            throw new IllegalArgumentException("Commodity with SKU code already exists: " + commodityBO.getSkuCode());
        }
        Commodity commodity = commodityConverter.toEntity(commodityBO);
        return commodityConverter.toBO(commodityRepository.save(commodity));
    }

    @Transactional
    public CommodityBO update(Long id, CommodityBO commodityBO) {
        Commodity commodity = commodityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Commodity not found with id: " + id));
        
        if (commodityBO.getSkuCode() != null && 
            !commodityBO.getSkuCode().equals(commodity.getSkuCode()) && 
            commodityRepository.existsBySkuCode(commodityBO.getSkuCode())) {
            throw new IllegalArgumentException("Commodity with SKU code already exists: " + commodityBO.getSkuCode());
        }

        commodity.setName(commodityBO.getName());
        commodity.setDescription(commodityBO.getDescription());
        commodity.setPrice(commodityBO.getPrice());
        commodity.setStockQuantity(commodityBO.getStockQuantity());
        commodity.setCategory(commodityBO.getCategory());
        commodity.setSkuCode(commodityBO.getSkuCode());
        commodity.setBrand(commodityBO.getBrand());
        commodity.setUnitOfMeasure(commodityBO.getUnitOfMeasure());
        commodity.setMinStockLevel(commodityBO.getMinStockLevel());
        commodity.setMaxStockLevel(commodityBO.getMaxStockLevel());
        commodity.setActive(commodityBO.isActive());

        return commodityConverter.toBO(commodityRepository.save(commodity));
    }

    @Transactional
    public void delete(Long id) {
        if (!commodityRepository.existsById(id)) {
            throw new EntityNotFoundException("Commodity not found with id: " + id);
        }
        commodityRepository.deleteById(id);
    }

    @Transactional
    public CommodityBO updateStock(Long id, int quantity) {
        Commodity commodity = commodityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Commodity not found with id: " + id));
        
        int newQuantity = commodity.getStockQuantity() + quantity;
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Insufficient stock for commodity: " + commodity.getName());
        }
        
        commodity.setStockQuantity(newQuantity);
        return commodityConverter.toBO(commodityRepository.save(commodity));
    }
} 