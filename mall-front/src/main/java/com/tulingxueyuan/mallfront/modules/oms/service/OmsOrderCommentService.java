package com.tulingxueyuan.mallfront.modules.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mallfront.dto.OrderCommentDTO;
import com.tulingxueyuan.mallfront.modules.oms.model.OmsOrderComment;

import java.util.List;

public interface OmsOrderCommentService extends IService<OmsOrderComment> {

    List<OrderCommentDTO> getOrderComment(Long orderId);
}
