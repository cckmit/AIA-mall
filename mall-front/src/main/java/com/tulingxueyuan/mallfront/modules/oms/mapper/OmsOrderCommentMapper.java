package com.tulingxueyuan.mallfront.modules.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tulingxueyuan.mallfront.dto.OrderCommentDTO;
import com.tulingxueyuan.mallfront.modules.oms.model.OmsOrderComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OmsOrderCommentMapper extends BaseMapper<OmsOrderComment> {

    List<OrderCommentDTO> getCommentByOrderId(Long orderId);

    List<OrderCommentDTO> getCommentByParentId(Long parentId);


}



