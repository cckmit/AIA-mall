package com.tulingxueyuan.mallfront.modules.pms.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author peipei
 * @since 2021-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_product_comment")
@ApiModel(value="PmsProductComment对象", description="")
public class PmsProductComment implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "评价的商品Id")
    private Integer productId;

    @ApiModelProperty(value = "文字评论")
    private String textComment;

    @ApiModelProperty(value = "图片评论，','分隔图片url")
    private String picComment;

    @ApiModelProperty(value = "评论日期")
    private Date commentTime;

    private Integer status;

    private Integer toCommentId;


}
