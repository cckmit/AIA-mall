package com.tulingxueyuan.mallfront.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.common.api.ResultCode;
import com.tulingxueyuan.mallfront.component.trade.alipay.utils.AlipayNotifyUtil;
import com.tulingxueyuan.mallfront.dto.*;
import com.tulingxueyuan.mallfront.modules.oms.model.OmsOrder;
import com.tulingxueyuan.mallfront.modules.oms.service.OmsOrderCommentService;
import com.tulingxueyuan.mallfront.modules.oms.service.OmsOrderService;
import com.tulingxueyuan.mallfront.modules.oms.service.TradeService;
import com.tulingxueyuan.mallfront.modules.sms.service.SmsCouponHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Name: OrderController
 * @Author peipei
 * @Date 2021/10/23
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OmsOrderService omsOrderService;

    @Autowired
    SmsCouponHistoryService smsCouponHistoryService;

    @Autowired
    OmsOrderCommentService omsOrderCommentService;


    @Autowired
    AlipayNotifyUtil alipayNotifyUtil;

    @Autowired
    TradeService tradeService;


    @GetMapping("/orderList")
    public CommonResult getUserOrders(OrderQueryDTO orderQueryDTO) {
        if (orderQueryDTO.getAmountSort() == null) {
            orderQueryDTO.setAmountSort(0);
        }
        IPage<OrderQueryDTO> userOrders = omsOrderService.getUserOrders(orderQueryDTO);
        return CommonResult.success(userOrders);
    }

    @GetMapping("/productDetail")
    public CommonResult getOrderProductDetail(@RequestParam("orderId") Long orderId) {
        OrderProductDetailDTO orderProductDetail=omsOrderService.getOrderProductDetail(orderId);
        return CommonResult.success(orderProductDetail);
    }

    @PostMapping("/status")
    public CommonResult updateOrderStatus(@RequestParam("orderId") Long orderId,
                                          @RequestParam("orderStatus") Integer orderStatus) {
        boolean updateOrderStatus=omsOrderService.updateOrderStatus(orderId,orderStatus);
        if (updateOrderStatus) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 获取此订单发表图片，文字评论接口
     *
     * @return
     */
    @GetMapping("/comment")
    public CommonResult getOrderComment(@RequestParam("orderId") Long orderId) {
        List<OrderCommentDTO> orderCommentDTOList = omsOrderCommentService.getOrderComment(orderId);
        return CommonResult.success(orderCommentDTOList);
    }


    /**
     * 创建新评论接口
     *
     * @param orderId
     * @param
     * @return
     */
    @PostMapping("/createOrderComment")
    public CommonResult createOrderComment(@RequestParam("orderId") Long orderId,
                                           @RequestParam("orderComment") String orderComment) {
        boolean createOrderComment=omsOrderService.createOrderComment(orderId,orderComment);
        if (createOrderComment) {
            return CommonResult.success(createOrderComment);
        } else {
            return CommonResult.failed();
        }
    }

    @GetMapping("/detail")
    public CommonResult orderDetail(@RequestParam("orderId") Long orderId) {
        OrderDetailDTO orderDetailDTO = omsOrderService.getOrderDetail(orderId);
        if (orderDetailDTO == null) {
            return CommonResult.failed(ResultCode.FORBIDDEN);
        }
        return CommonResult.success(orderDetailDTO);
    }

    @GetMapping("/delete")
    public CommonResult deleteOrder(@RequestParam("orderId") Long orderId) {
        boolean remove = omsOrderService.removeById(orderId);
        if (remove) {
            return CommonResult.success(remove);
        } else {
            return CommonResult.failed();
        }
    }





/*
    @PostMapping("/generateOrder")
    public CommonResult generateOrder(@RequestBody GenerateOrderDTO generateOrderDTO) {
        OmsOrder omsOrder = omsOrderService.generateOrder(generateOrderDTO);
        return CommonResult.success(omsOrder.getId());
    }
*/



    @PostMapping("/alipay")
    public CommonResult getTradeQrCode(@RequestParam("orderId") Long orderId) {
        return tradeService.alipayTrade(orderId);
    }

    /**
     * 支付宝支付成功的回调接口
     *
     * @param request
     * @return
     */
    @PostMapping("/alipay/fallback/")
    public void alipayFallback(HttpServletRequest request) throws Exception {
        Map<String, String> alipaySuccessMap = alipayNotifyUtil.convertRequestParamsToMap(request);
        String paySuccessOrderSn = alipaySuccessMap.get("out_trade_no");
/*
        验签  ：去除sign和sign_type 参数 进行验签， checkV1 会在方法中去除，CheckV2不会去除sign_type，所以要手动排除
        boolean signVerified = AlipaySignature.rsaCheckV2(alipaySuccessMap, alipay_public_key, "utf-8", "RSA2"); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        if (signVerified) {
            // 订单id
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
                log.info("======验签成功=======,{}",out_trade_no);
        } else {
            System.out.println("======验签失败======");
        }
*/
        // 通过order获取该订单的支付方式
        OmsOrder paySuccessOrder=omsOrderService.getOrderByOrderSn(paySuccessOrderSn);
        omsOrderService.paySuccess(paySuccessOrderSn,paySuccessOrder.getPayType());
        log.info("============支付宝支付回调成功!=============时间:{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    /**
     * 微信支付成功的回调接口
     *
     * @param request
     * @return
     */
    @PostMapping("/wechatPay/fallback")
    public void wechatPayFallback(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println(parameterMap);
        // omsOrderService.paySuccess();
        log.info("============微信支付回调成功!===============时间:{}", new Date().toString());
    }

    @GetMapping("/status")
    public CommonResult getOrderStatus(@RequestParam("orderId") Long orderId) {
        log.info("===========轮询订单状态===========");
        OmsOrder orderById = omsOrderService.getById(orderId);
        return CommonResult.success(orderById.getStatus());
    }


}
