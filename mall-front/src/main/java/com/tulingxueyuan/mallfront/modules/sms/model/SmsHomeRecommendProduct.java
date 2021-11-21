package com.tulingxueyuan.mallfront.modules.sms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 人气推荐商品表
 * </p>
 *
 * @author peipei
 * @since 2021-10-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_home_recommend_product")
@ApiModel(value="SmsHomeRecommendProduct对象", description="人气推荐商品表")
public class SmsHomeRecommendProduct implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long productId;

    private String productName;

    private Integer recommendStatus;

    private Integer sort;


}
