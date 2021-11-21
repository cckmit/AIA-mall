package com.tulingxueyuan.mallfront.modules.pms.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品信息
 * </p>
 *
 * @author XuShu
 * @since 2021-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_product")
@ApiModel(value="PmsProduct对象", description="商品信息")
public class PmsProduct implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long brandId;

    private Long productCategoryId;


    private Long productAttributeCategoryId;

    private String name;

    private String pic;

    @ApiModelProperty(value = "货号")
    private String productSn;

    @ApiModelProperty(value = "删除状态：0->未删除；1->已删除")
    private Integer deleteStatus;

    @ApiModelProperty(value = "上架状态：0->下架；1->上架")
    private Integer publishStatus;

    @ApiModelProperty(value = "新品状态:0->不是新品；1->新品")
    private Integer newStatus;

    @ApiModelProperty(value = "推荐状态；0->不推荐；1->推荐")
    private Integer recommandStatus;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    private BigDecimal price;


    @ApiModelProperty(value = "商品描述")
    private String description;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "库存预警值")
    private Integer lowStock;

    private String note;

    @ApiModelProperty(value = "画册图片，连产品图片限制为5张，以逗号分割")
    private String albumPics;


    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "商品分类名称")
    private String productCategoryName;


}
