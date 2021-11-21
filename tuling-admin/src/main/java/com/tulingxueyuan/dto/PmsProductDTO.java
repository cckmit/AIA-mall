package com.tulingxueyuan.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description:
 * @Name: PmsProductDto
 * @Author peipei
 * @Date 2021/9/11
 */

/* keyword: '',
         pageNum: 1,
         pageSize: 5,
         publishStatus: null,
         verifyStatus: null,
         productSn: null,
         productCategoryId: null,
         brandId: null*/
@Data
@ApiModel(value = "ProductConditionDTO商品列表筛选条件", description = "用于商品列表筛选条件参数接收")
public class PmsProductDTO {
    private Integer pageNum;

    private Integer pageSize;

    private String keyword;

    private Integer publishStatus;

    @ApiModelProperty("商品审核状态,0-->为通过,1-->通过")
    private Integer verifyStatus;

    @ApiModelProperty("商品货号")
    private String productSn;

    private Long brandId;

    private Long productCategoryId;

    @ApiModelProperty("商品的删除状态，0-->未被删除，1-->在回收站")
    private Long deleteStatus;


}
