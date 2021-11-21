package com.tulingxueyuan.mallfront.modules.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mallfront.dto.OrderCommentDTO;
import com.tulingxueyuan.mallfront.modules.oms.mapper.OmsCartItemMapper;
import com.tulingxueyuan.mallfront.modules.oms.mapper.OmsOrderCommentMapper;
import com.tulingxueyuan.mallfront.modules.oms.model.OmsCartItem;
import com.tulingxueyuan.mallfront.modules.oms.model.OmsOrderComment;
import com.tulingxueyuan.mallfront.modules.oms.service.OmsOrderCommentService;
import com.tulingxueyuan.mallfront.modules.ums.model.UmsMember;
import com.tulingxueyuan.mallfront.modules.ums.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Description:
 * @Name: OmsOrderCommentServiceImpl
 * @Author peipei
 * @Date 2021/11/17
 */
@Service
public class OmsOrderCommentServiceImpl extends ServiceImpl<OmsOrderCommentMapper, OmsOrderComment> implements OmsOrderCommentService {
    @Autowired
    OmsOrderCommentMapper omsOrderCommentMapper;

    @Autowired
    UmsMemberService umsMemberService;


    @Override
    public List<OrderCommentDTO> getOrderComment(Long orderId) {
        List<OrderCommentDTO> commentByOrderId = omsOrderCommentMapper.getCommentByOrderId(orderId);
        List<OrderCommentDTO> resultComment = getSons(commentByOrderId);
        //填充所有评论对象
        for (OrderCommentDTO orderCommentDTO : resultComment) {
            if (orderCommentDTO.getChildComment()!=null) {
                for (OrderCommentDTO orderCommentChildDTO: orderCommentDTO.getChildComment()){
                    //被回复者的姓名
                    OmsOrderComment orderParentComment = this.getById(orderCommentChildDTO.getToCommentId());
                    UmsMember umsMember = umsMemberService.getById(orderParentComment.getMemberId());
                    orderCommentChildDTO.setReplyName(umsMember.getUsername());
                    UmsMember commentMember = umsMemberService.getById(orderCommentChildDTO.getMemberId());
                    orderCommentChildDTO.setCommentName(commentMember.getUsername());
                    //    回复者的头像
                    orderCommentChildDTO.setAvatar(commentMember.getIcon());
                }
            }
            //    回复者的姓名
            UmsMember commentMember = umsMemberService.getById(orderCommentDTO.getMemberId());
            orderCommentDTO.setCommentName(commentMember.getUsername());
            //    回复者的头像
            orderCommentDTO.setAvatar(commentMember.getIcon());
        }

        return resultComment;

    }

    private List<OrderCommentDTO> getSons(Long parentId) {
        return omsOrderCommentMapper.getCommentByParentId(parentId);
    }


    private List<OrderCommentDTO> getSons(List<OrderCommentDTO> parents) {
        if (parents == null || parents.size() == 0) {
            return null;
        }
        for (OrderCommentDTO parent : parents) {
            Long parentId = parent.getId();
            List<OrderCommentDTO> sonCommentVos = getSons(parentId);
            //递归找子评论
            parent.setChildComment(getSons(sonCommentVos));
        }
        return parents;
    }


}
