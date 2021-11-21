package com.tulingxueyuan.mall.modules.ums.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户标签表
 * </p>
 *
 * @author peipei
 * @since 2021-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_member_star")
@ApiModel(value="UmsMemberStar对象", description="用户标签表")
public class UmsMemberStar implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Integer memeberId;

    private String starStoreId;

    private String starProductId;


}
