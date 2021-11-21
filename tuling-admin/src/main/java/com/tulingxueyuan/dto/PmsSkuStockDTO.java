package com.tulingxueyuan.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Description:
 * @Name: PmsSkuStockDto
 * @Author peipei
 * @Date 2021/9/15
 */
@Data
@ApiModel(value="PmsSkuStockDto商品库存状态", description="用于商品库存状态条件参数接收")
public class PmsSkuStockDTO {
    //sku编号
    private Long id;

    //销售价格
    private double price;

    //商品库存
    private Long stock;

    //商品库存预警值
    private Long low_stock;

}
