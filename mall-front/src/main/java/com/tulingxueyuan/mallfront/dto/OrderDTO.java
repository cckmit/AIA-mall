package com.tulingxueyuan.mallfront.dto;

import com.tulingxueyuan.mallfront.modules.oms.model.OmsOrder;
import com.tulingxueyuan.mallfront.modules.oms.model.OmsOrderItem;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Name: OrderDTO
 * @Author peipei
 * @Date 2021/10/24
 */
@Data
public class OrderDTO extends OmsOrder {

    private List<OmsOrderItem> orderItemList;

}
