package com.tulingxueyuan.mallfront.modules.ums.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 会员表
 * </p>
 *
 * @author peipei
 * @since 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_member")
@ApiModel(value="UmsMember对象", description="会员表")
public class UmsMember implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @NotNull
    @ApiModelProperty(value = "用户名")
    private String username;

    @NotNull
    @ApiModelProperty(value = "密码")
    private String password;


    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "帐号启用状态:0->禁用；1->启用")
    private Integer status;

    @ApiModelProperty(value = "注册时间")
    private Date createTime;

    @ApiModelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "性别：0->未知；1->男；2->女")
    private Integer gender;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "所做城市")
    private String city;

    @ApiModelProperty(value = "职业")
    private String job;

    @ApiModelProperty(value = "个性签名")
    private String personalizedSignature;

    @ApiModelProperty(value = "积分")
    private Integer integration;


}
