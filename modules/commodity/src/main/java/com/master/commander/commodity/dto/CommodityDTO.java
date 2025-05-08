package com.master.commander.commodity.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CommodityDTO {
    private Long id;
    private String name;
    private String description;
    private String formattedPrice; // 格式化后的价格
    private Integer stock;
    private String category;
    private String maskedSkuCode; // 脱敏后的SKU码
    private String brand;
    private String unit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean active;

    // 用于创建和更新的字段
    private BigDecimal price;
    private String skuCode;
    private Integer minStockLevel;
    private Integer maxStockLevel;
} 