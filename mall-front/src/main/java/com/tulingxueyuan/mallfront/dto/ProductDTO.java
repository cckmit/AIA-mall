package com.tulingxueyuan.mallfront.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/***
 * @Author peipei
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="商品的数据传输对象", description="商品的数据传输对象")
public class ProductDTO {
    private Long id;
    private String name;
    private String pic;
    @ApiModelProperty(value = "商品价")
    private BigDecimal price;

}
