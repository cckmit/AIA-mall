package com.tulingxueyuan.dto;

import com.tulingxueyuan.mall.modules.pms.model.*;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Description:
 * @Name: PmsProductSaveDto
 * @Author peipei
 * @Date 2021/9/19
 */
@Data
public class PmsProductSaveDTO extends PmsProduct {
    private List<PmsMemberPrice> memberPriceList;

    private List<PmsProductFullReduction> productFullReductionList;

    private List<PmsProductLadder> productLadderList;

    private List<PmsProductAttributeValue> productAttributeValueList;

    @Size(min = 1,message = "SKU至少要有一项")
    private List<PmsSkuStock> skuStockList;

}
