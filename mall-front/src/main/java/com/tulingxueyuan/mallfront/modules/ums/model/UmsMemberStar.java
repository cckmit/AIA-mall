package com.tulingxueyuan.mallfront.modules.ums.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
@ApiModel(value="UmsMemberStar对象", description="用户收藏表")
public class UmsMemberStar implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long memberId;

    private String starStoreId;

    private String starProductId;


}
