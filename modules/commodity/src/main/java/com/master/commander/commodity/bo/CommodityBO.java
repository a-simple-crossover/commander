package com.master.commander.commodity.bo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CommodityBO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String category;
    private String skuCode;
    private String brand;
    private String unitOfMeasure;
    private Integer minStockLevel;
    private Integer maxStockLevel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean active;

    // 业务方法
    public String getFormattedPrice() {
        return price != null ? price.setScale(2).toString() : null;
    }

    public String getMaskedSkuCode() {
        if (skuCode == null) {
            return null;
        }
        if (skuCode.length() > 8) {
            return skuCode.substring(0, 4) + "****" + 
                   skuCode.substring(skuCode.length() - 4);
        }
        return skuCode;
    }

    public boolean isLowStock() {
        return minStockLevel != null && 
               stockQuantity != null && 
               stockQuantity <= minStockLevel;
    }
} 