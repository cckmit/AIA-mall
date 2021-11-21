package com.tulingxueyuan.mallfront.modules.oms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @Description:
 * @Name: OmsOrderComment
 * @Author peipei
 * @Date 2021/11/17
 */

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oms_order_comment")
@ApiModel(value="OmsOrderComment", description="订单评论表")

public class OmsOrderComment {

    private static final long serialVersionUID=1L;

    //主键id
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    //订单id
    private Long orderId;

    //    评论人id
    private Long memberId;

    //    评论的评论id
    private Long toCommentId;

    //    文本评论
    private String textComment;

    //    评论时间
    private Date commentTime;

    //    评论等级
    private Integer status;



}
