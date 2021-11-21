package com.tulingxueyuan.dto;

import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description:
 * @Name: PmsProductDetailDto
 * @Author peipei
 * @Date 2021/9/19
 */
@Data
@NoArgsConstructor
public class PmsProductDetailDTO extends PmsProductSaveDTO{

    @ApiModelProperty("一级分类id")
    private Long cateParentId;

}
