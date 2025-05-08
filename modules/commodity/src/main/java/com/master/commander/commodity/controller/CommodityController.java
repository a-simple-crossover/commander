package com.master.commander.commodity.controller;

import com.master.commander.commodity.bo.CommodityBO;
import com.master.commander.commodity.converter.CommodityConverter;
import com.master.commander.commodity.dto.CommodityDTO;
import com.master.commander.commodity.service.CommodityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/commodities")
@RequiredArgsConstructor
public class CommodityController {
    private final CommodityService commodityService;
    private final CommodityConverter commodityConverter;

    @GetMapping
    public ResponseEntity<List<CommodityDTO>> getAllCommodities(
            @RequestParam(required = false) Boolean activeOnly) {
        List<CommodityBO> commodities = activeOnly != null && activeOnly 
            ? commodityService.findAllActive() 
            : commodityService.findAll();
        return ResponseEntity.ok(commodityConverter.toDTOList(commodities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommodityDTO> getCommodityById(@PathVariable Long id) {
        CommodityBO commodity = commodityService.findById(id);
        return ResponseEntity.ok(commodityConverter.toDTO(commodity));
    }

    @GetMapping("/sku/{skuCode}")
    public ResponseEntity<CommodityDTO> getCommodityBySkuCode(@PathVariable String skuCode) {
        CommodityBO commodity = commodityService.findBySkuCode(skuCode);
        return ResponseEntity.ok(commodityConverter.toDTO(commodity));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<CommodityDTO>> getCommoditiesByCategory(@PathVariable String category) {
        List<CommodityBO> commodities = commodityService.findByCategory(category);
        return ResponseEntity.ok(commodityConverter.toDTOList(commodities));
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<CommodityDTO>> getCommoditiesByBrand(@PathVariable String brand) {
        List<CommodityBO> commodities = commodityService.findByBrand(brand);

        return ResponseEntity.ok(commodityConverter.toDTOList(commodities));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CommodityDTO>> searchCommodities(@RequestParam String name) {
        List<CommodityBO> commodities = commodityService.searchByName(name);
        return ResponseEntity.ok(commodityConverter.toDTOList(commodities));
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<CommodityDTO>> getCommoditiesByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        List<CommodityBO> commodities = commodityService.findByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(commodityConverter.toDTOList(commodities));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<CommodityDTO>> getLowStockCommodities() {
        List<CommodityBO> commodities = commodityService.findLowStockCommodities();
        return ResponseEntity.ok(commodityConverter.toDTOList(commodities));
    }

    @PostMapping
    public ResponseEntity<CommodityDTO> createCommodity(@RequestBody CommodityDTO commodityDTO) {
        CommodityBO commodityBO = commodityConverter.toBO(commodityDTO);
        CommodityBO createdCommodity = commodityService.create(commodityBO);
        return new ResponseEntity<>(commodityConverter.toDTO(createdCommodity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommodityDTO> updateCommodity(
            @PathVariable Long id,
            @RequestBody CommodityDTO commodityDTO) {
        CommodityBO commodityBO = commodityConverter.toBO(commodityDTO);
        CommodityBO updatedCommodity = commodityService.update(id, commodityBO);
        return ResponseEntity.ok(commodityConverter.toDTO(updatedCommodity));
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<CommodityDTO> updateStock(
            @PathVariable Long id,
            @RequestParam int quantity) {
        CommodityBO updatedCommodity = commodityService.updateStock(id, quantity);
        return ResponseEntity.ok(commodityConverter.toDTO(updatedCommodity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommodity(@PathVariable Long id) {
        commodityService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 