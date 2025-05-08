package com.master.commander.commodity.converter;

import com.master.commander.commodity.bo.CommodityBO;
import com.master.commander.commodity.dto.CommodityDTO;
import com.master.commander.commodity.entity.Commodity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommodityConverter {
    // Entity -> BO
    CommodityBO toBO(Commodity commodity);
    List<CommodityBO> toBOList(List<Commodity> commodities);
    
    // BO -> Entity
    @Mapping(target = "id", ignore = true)
    Commodity toEntity(CommodityBO commodityBO);
    
    // BO -> DTO
    @Mapping(target = "formattedPrice", source = "formattedPrice")
    @Mapping(target = "maskedSkuCode", source = "maskedSkuCode")
    @Mapping(target = "stock", source = "stockQuantity")
    @Mapping(target = "unit", source = "unitOfMeasure")
    CommodityDTO toDTO(CommodityBO commodityBO);
    List<CommodityDTO> toDTOList(List<CommodityBO> commodityBOs);
    
    // DTO -> BO
    @Mapping(target = "stockQuantity", source = "stock")
    @Mapping(target = "unitOfMeasure", source = "unit")
    CommodityBO toBO(CommodityDTO commodityDTO);
} 