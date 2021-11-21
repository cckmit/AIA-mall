package com.tulingxueyuan.mallfront.dto;

import com.tulingxueyuan.mallfront.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mallfront.modules.pms.model.PmsProductAttributeCategory;
import com.tulingxueyuan.mallfront.modules.pms.model.PmsProductAttributeValue;
import com.tulingxueyuan.mallfront.modules.pms.model.PmsSkuStock;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Name: ProductDetailDTO
 * @Author peipei
 * @Date 2021/10/16
 */
@Data
public class ProductDetailDTO extends PmsProduct {
    private List<PmsSkuStock> skuStockList;

    private List<PmsProductAttributeValue> productAttributeValueList;
}
