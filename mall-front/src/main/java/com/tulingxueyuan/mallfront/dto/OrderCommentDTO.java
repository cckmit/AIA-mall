package com.tulingxueyuan.mallfront.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Name: OrderCommentDTO
 * @Author peipei
 * @Date 2021/11/17
 */
@ApiModel("订单的图片，文字评论DTO")
@Data
public class OrderCommentDTO {

    //主键id
    private Long id;

    //    订单id
    private Long orderId;

    //    文字评论
    private String textComment;

    //    图片评论(url)
    private String picComment;

    //评论时间
    private Date commentTime;

    //评论给谁
    private String replyName;

    private Long memberId;

    //评论人姓名
    private String commentName;


    //评论人头像
    private String avatar;

    //评论等级
    private Integer status;

    private Long toCommentId;

    //多级评论
    private List<OrderCommentDTO> childComment=new ArrayList<>();
}
