package com.tulingxueyuan.mall.modules.ums.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户店铺表
 * </p>
 *
 * @author peipei
 * @since 2021-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_member_store")
@ApiModel(value="UmsMemberStore对象", description="用户店铺表")
public class UmsMemberStore implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "店铺所属的用户id")
    private Integer memberId;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "店铺描述")
    private String description;

    @ApiModelProperty(value = "开店时间")
    @TableField("store_openTime")
    private Integer storeOpentime;


}
