package com.tulingxueyuan.mallfront.modules.oms.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.exception.ApiException;
import com.tulingxueyuan.mallfront.component.TradePayProp;
import com.tulingxueyuan.mallfront.dto.*;
import com.tulingxueyuan.mallfront.modules.oms.mapper.OmsCartItemMapper;
import com.tulingxueyuan.mallfront.modules.oms.mapper.OmsOrderCommentMapper;
import com.tulingxueyuan.mallfront.modules.oms.model.*;
import com.tulingxueyuan.mallfront.modules.oms.mapper.OmsOrderMapper;
import com.tulingxueyuan.mallfront.modules.oms.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mallfront.modules.pms.model.*;
import com.tulingxueyuan.mallfront.modules.pms.service.*;
import com.tulingxueyuan.mallfront.modules.sms.model.SmsCoupon;
import com.tulingxueyuan.mallfront.modules.sms.service.SmsCouponService;
import com.tulingxueyuan.mallfront.modules.ums.model.UmsMember;
import com.tulingxueyuan.mallfront.modules.ums.model.UmsMemberReceiveAddress;
import com.tulingxueyuan.mallfront.modules.ums.service.RedisService;
import com.tulingxueyuan.mallfront.modules.ums.service.UmsMemberReceiveAddressService;
import com.tulingxueyuan.mallfront.modules.ums.service.UmsMemberService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2021-10-17
 */
@Slf4j
@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements OmsOrderService {
    @Autowired
    OmsOrderMapper omsOrderMapper;

    @Autowired
    OmsOrderSettingService omsOrderSettingService;

    @Autowired
    OmsOrderCommentService omsOrderCommentService;

    @Autowired
    PmsSkuStockService pmsSkuStockService;

    @Autowired
    PmsProductService pmsProductService;

    @Autowired
    PmsProductAttributeService pmsProductAttributeService;

    @Autowired
    PmsBrandService pmsBrandService;

    @Autowired
    UmsMemberService umsMemberService;


    @Autowired
    PmsFreightTemplateService pmsFreightTemplateService;

    @Autowired
    PmsProductAttributeCategoryService pmsProductAttributeCategoryService;

    @Autowired
    OmsOrderCommentMapper omsOrderCommentMapper;

    @Autowired
    UmsMemberReceiveAddressService umsMemberReceiveAddressService;

    @Autowired
    SmsCouponService smsCouponService;

    @Autowired
    RedisService redisService;

    @Autowired
    OmsCartItemMapper omsCartItemMapper;

    @Autowired
    TradePayProp tradePayProp;

    @Value("${redis.key.prefix.orderId}")
    String REDIS_KEY_PREFIX_ORDER_ID;

    /**
     * 返回当前登录的用户实体
     *
     * @return
     */
    private UmsMember getCurrentMember() {
        return umsMemberService.getCurrentMember();
    }


    @Override
    public IPage<OrderQueryDTO> getUserOrders(OrderQueryDTO orderQueryDTO) {
        UmsMember currentMember = getCurrentMember();
        Page page = new Page(orderQueryDTO.getPageNum(), orderQueryDTO.getPageSize());
        //通过productName属性模糊查询出所有sku，再和订单中的进行匹配
        LambdaQueryWrapper<PmsProduct> productWrapper = new QueryWrapper<PmsProduct>().lambda();
        productWrapper.like(PmsProduct::getName, orderQueryDTO.getProductName());
        List<PmsProduct> pmsProducts = pmsProductService.list(productWrapper);
        //获取productIds
        List<Long> productIds = pmsProducts.stream().map(PmsProduct::getId).collect(Collectors.toList());
        if (productIds.size() == 0) {
            return new Page<OrderQueryDTO>();
        }
        IPage<OrderQueryDTO> userOrders = omsOrderMapper.getUserOrders(page, orderQueryDTO, currentMember.getId(), productIds);
        List<OrderQueryDTO> ordersRecords = userOrders.getRecords();
        for (OrderQueryDTO ordersRecord : ordersRecords) {
            PmsProduct productById = pmsProductService.getById(ordersRecord.getProductId());
            ordersRecord.setProductName(productById.getName());
        }
        return userOrders;
    }

    @Override
    public OrderDetailDTO getOrderDetail(Long orderId) {
        //先由orderId确定该用户是否拥有该订单
        UmsMember currentMember = getCurrentMember();
        LambdaQueryWrapper<OmsOrder> omsOrderLambdaQueryWrapper = new QueryWrapper<OmsOrder>().lambda();
        omsOrderLambdaQueryWrapper.eq(OmsOrder::getMemberId, currentMember.getId());
        List<OmsOrder> currentMemberOmsOrderList = this.list(omsOrderLambdaQueryWrapper);
        List<Long> OmsOrderIdList = currentMemberOmsOrderList.stream().map(OmsOrder::getId).collect(Collectors.toList());
        if (!OmsOrderIdList.contains(orderId)) {
            //改订单不属于当前用户
            return null;
        }
        OrderDetailDTO orderDetail = omsOrderMapper.getOrderDetail(orderId);
        //订单的过期支付时间
        return orderDetail;
    }

    @Override
    @Transactional
    public OmsOrder generateOrder(GenerateOrderDTO generateOrderDTO) {
        //1：查询出购物车内每件商品的实体，判断每件商品实际库存（库存-锁定库存）是否>0
        LambdaQueryWrapper<OmsCartItem> lambdaQueryWrapper = new QueryWrapper<OmsCartItem>().lambda();
        lambdaQueryWrapper.eq(OmsCartItem::getMemberId, getCurrentMember().getId())
                .in(OmsCartItem::getId, generateOrderDTO.getItemIds());
        List<CartItemsDTO> cartItemsDTOByIds = omsCartItemMapper.getCartProductDTOByIds(lambdaQueryWrapper);
        String isOutSkuStockName = hasSkuStock(cartItemsDTOByIds);
        if (StrUtil.isNotBlank(isOutSkuStockName)) {
            throw new ApiException("您的手速慢了，" + isOutSkuStockName + "已被抢光");
        }
        //  2：  创建新订单实体
        OmsOrder omsOrder = createNewOrder(generateOrderDTO, cartItemsDTOByIds);
        //将创建好的订单实体存储在数据库中
        this.save(omsOrder);
        //  3：  将购物车内的所有商品添加到order_items中
        //    4：锁定库存
        lockStock(omsOrder);
        //   5： 将该用户购物车内的商品清空
        return omsOrder;

    }

    @Override
    public OmsOrder getOrderByOrderSn(String orderSn) {
        LambdaQueryWrapper<OmsOrder> orderLambdaQueryWrapper = new QueryWrapper<OmsOrder>().lambda();
        orderLambdaQueryWrapper.eq(OmsOrder::getOrderSn, orderSn);
        return this.getOne(orderLambdaQueryWrapper);
    }


    /**
     * 定时任务，每个星期一自动删除过期的订单
     */

    @Override
    public void cancelOverTimeOrder() {
        //先查询出所有已经超时的所有订单
        OmsOrderSetting omsOrderSetting = omsOrderSettingService.getById(1L);
        Integer normalOrderOvertime = omsOrderSetting.getNormalOrderOvertime();
        DateTime offsetTime = DateUtil.offset(new Date(), DateField.MINUTE, -normalOrderOvertime);
        LambdaQueryWrapper<OmsOrder> orderLambdaQueryWrapper = new QueryWrapper<OmsOrder>().lambda();
        orderLambdaQueryWrapper.le(OmsOrder::getCreateTime, offsetTime);
        List<OmsOrder> overTimeOrders = this.list(orderLambdaQueryWrapper);
        //将所有超时订单的status更改为4(已关闭),并且提取出所有超时订单的id
        for (OmsOrder overTimeOrder : overTimeOrders) {
            overTimeOrder.setStatus(4);
        }
        //如果没有订单超时则直接return
        if (CollectionUtils.isEmpty(overTimeOrders)) {
            log.info("没有超时订单");
            return;
        }
        //批量更新订单状态
        this.updateBatchById(overTimeOrders);
        List<Long> overTimeOrderIds = overTimeOrders.stream()
                .map(OmsOrder::getId).collect(Collectors.toList());
        //记录超时订单的订单号
        log.info("超时的订单号-->{}", overTimeOrderIds.toString());
        //    通过超时订单的id,找出每个订单对应的order_items
        LambdaQueryWrapper<OmsOrderItem> orderItemLambdaQueryWrapper = new QueryWrapper<OmsOrderItem>().lambda();
        orderItemLambdaQueryWrapper.in(OmsOrderItem::getOrderId, overTimeOrderIds);
        // List<OmsOrderItem> overTimeOrderItems = omsOrderItemService.list(orderItemLambdaQueryWrapper);
        // for (OmsOrderItem overTimeOrderItem : overTimeOrderItems) {
        //     //    更新每件商品的sku_stock
        //     LambdaUpdateWrapper<PmsSkuStock> stockLambdaUpdateWrapper = new UpdateWrapper<PmsSkuStock>().lambda();
        //     stockLambdaUpdateWrapper.eq(PmsSkuStock::getId, overTimeOrderItem.getProductSkuId())
        //             .setSql("lock_stock=lock_stock-" + overTimeOrderItem.getProductQuantity());
        //     pmsSkuStockService.update(stockLambdaUpdateWrapper);
        // }
    }

    /**
     * 支付成功后的回调接口，(支付宝，微信共用)
     * 1：修改订单状态为1（已付款，待发货）,更新paymentTime.更新支付方式(payType)
     * 2：更新锁定库存，实际库存
     * 3：删除二维码
     *
     * @param paySuccessOrderSn
     */
    @Override
    public void paySuccess(String paySuccessOrderSn, Integer payType) {
        //  1：修改订单状态为1（已付款，待发货）,更新paymentTime.更新支付方式(payType)
        LambdaUpdateWrapper<OmsOrder> orderLambdaUpdateWrapper = new UpdateWrapper<OmsOrder>().lambda();
        orderLambdaUpdateWrapper.set(OmsOrder::getStatus, 1)
                .set(OmsOrder::getPaymentTime, new Date())
                .eq(OmsOrder::getPayType, payType)
                .eq(OmsOrder::getOrderSn, paySuccessOrderSn);
        boolean updateByOrderId = this.update(orderLambdaUpdateWrapper);
        if (!updateByOrderId) {
            log.error("订单编号为{}的订单支付成功，但是订单状态更新失败!", paySuccessOrderSn);
            throw new ApiException("订单支付成功，但是订单状态更新失败!");
        }

        // 2：更新锁定库存，实际库存
        LambdaQueryWrapper<OmsOrderItem> orderItemLambdaQueryWrapper = new QueryWrapper<OmsOrderItem>().lambda();
        orderItemLambdaQueryWrapper.in(OmsOrderItem::getOrderSn, paySuccessOrderSn);
        // List<OmsOrderItem> paySuccessOrderItems = omsOrderItemService.list(orderItemLambdaQueryWrapper);
        //根据orderItem的skuId找到对应商品的真实库存
        // for (OmsOrderItem paySuccessOrderItem : paySuccessOrderItems) {
        //     LambdaUpdateWrapper<PmsSkuStock> stockLambdaUpdateWrapper = new UpdateWrapper<PmsSkuStock>().lambda();
        //     stockLambdaUpdateWrapper.setSql("lock_stock=lock_stock-" + paySuccessOrderItem.getProductQuantity())
        //             .setSql("stock=stock-" + paySuccessOrderItem.getProductQuantity())
        //             .eq(PmsSkuStock::getId, paySuccessOrderItem.getProductSkuId());
        //     pmsSkuStockService.update(stockLambdaUpdateWrapper);
        // }
        // 3：删除二维码
        String fileName = String.format("/qr-%s.png", paySuccessOrderSn);
        String filePath = tradePayProp.getStorePath() + fileName;
        // 如果二维码存在
        if (FileUtil.exist(filePath) && FileUtil.isFile(filePath)) {
            // 删除
            FileUtil.del(filePath);
        }
    }


    /**
     * 获取订单的商品详情
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderProductDetailDTO getOrderProductDetail(Long orderId) {
        OrderProductDetailDTO orderProductDetailDTO = new OrderProductDetailDTO();
        //    先通过orderId获取该订单的productName,skuId
        OmsOrder orderById = this.getById(orderId);
        PmsProduct productById = pmsProductService.getById(orderById.getProductId());
        //    设置商品数量
        orderProductDetailDTO.setProductQuantity(orderById.getProductQuantity());
        //设置商品名称
        orderProductDetailDTO.setProductName(productById.getName());
        //设置商品品牌名称
        PmsBrand brandById = pmsBrandService.getById(productById.getBrandId());
        orderProductDetailDTO.setProductBrand(brandById.getName());
        //    设置商品图片
        orderProductDetailDTO.setProductPic(productById.getPic());
        //设置商品的规格参数,sku原价
        PmsSkuStock skuById = pmsSkuStockService.getById(orderById.getSkuId());
        orderProductDetailDTO.setProductAttribute(skuById.getSpu1() + " / " + skuById.getSpu2() + " / " + skuById.getSpu3());
        orderProductDetailDTO.setProductSkuPrice(skuById.getPrice());
        return orderProductDetailDTO;
    }

    @Override
    public boolean createOrderComment(Long orderId, String orderComment) {
        OmsOrderComment omsOrderComment = new OmsOrderComment();
        OmsOrder commentOrder = this.getById(orderId);
        //通过发货人姓名找出被评论人
        omsOrderComment.setOrderId(commentOrder.getId());
        omsOrderComment.setMemberId(commentOrder.getMemberId());
        //一级评论:0
        omsOrderComment.setToCommentId(0L);
        omsOrderComment.setTextComment(orderComment);
        omsOrderComment.setCommentTime(new Date());
        omsOrderComment.setStatus(0);
        return omsOrderCommentService.save(omsOrderComment);
    }

    @Override
    public boolean updateOrderStatus(Long orderId,Integer orderStatus) {
        LambdaUpdateWrapper<OmsOrder> omsOrderLambdaUpdateWrapper = new UpdateWrapper<OmsOrder>().lambda();
        if (orderStatus == 0) {
            omsOrderLambdaUpdateWrapper.set(OmsOrder::getStatus,1).eq(OmsOrder::getId,orderId);
        }
        if (orderStatus == 1) {
            omsOrderLambdaUpdateWrapper.set(OmsOrder::getStatus,2).eq(OmsOrder::getId,orderId);
        }
        return this.update(omsOrderLambdaUpdateWrapper);

    }


    /**
     * 根据order_id查找出order_items_id itemsIdList集合，由itemsIdList集合new出一个<product_skuId,quantity>的HashMap,
     * 接着再再从product_sku_stock表中缩减相应的库存(lock_stock+=quantity)
     *
     * @param omsOrder
     */
    private void lockStock(OmsOrder omsOrder) {
        LambdaQueryWrapper<OmsOrderItem> orderItemLambdaQueryWrapper = new QueryWrapper<OmsOrderItem>().lambda();
        orderItemLambdaQueryWrapper.eq(OmsOrderItem::getOrderId, omsOrder.getId());
        // List<OmsOrderItem> omsOrderItemList = omsOrderItemService.list(orderItemLambdaQueryWrapper);
        // for (OmsOrderItem omsOrderItem : omsOrderItemList) {
        //     LambdaUpdateWrapper<PmsSkuStock> pmsSkuStockLambdaUpdateWrapper = new UpdateWrapper<PmsSkuStock>().lambda();
        //     pmsSkuStockLambdaUpdateWrapper.eq(PmsSkuStock::getId, omsOrderItem.getProductSkuId())
        //             .setSql("lock_stock=lock_stock+" + omsOrderItem.getProductQuantity());
        //     pmsSkuStockService.update(pmsSkuStockLambdaUpdateWrapper);
        // }

    }



    /**
     * 生成订单实体类
     *
     * @param generateOrderDTO
     * @return
     */
    private OmsOrder createNewOrder(GenerateOrderDTO generateOrderDTO, List<CartItemsDTO> cartItemsDTOByIds) {
        OmsOrder omsOrder = new OmsOrder();
        UmsMember currentMember = getCurrentMember();
        //"订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单"
        omsOrder.setStatus(0);
        //    订单的用户id
        omsOrder.setMemberId(currentMember.getId());
        //     订单创建时间
        omsOrder.setCreateTime(new Date());
        //订单修改时间（新订单默认修改时间为订单创建时间）
        omsOrder.setModifyTime(new Date());
        //根据收货地址id查找出订单收货人，手机号
        UmsMemberReceiveAddress umsMemberReceiveAddress = umsMemberReceiveAddressService.getById(generateOrderDTO.getMemberReceiveAddressId());
        //订单收货人
        omsOrder.setReceiverName(umsMemberReceiveAddress.getName());
        //订单收货人的手机号
        omsOrder.setReceiverPhone(umsMemberReceiveAddress.getPhoneNumber());
        //订单确认收货状态：0->未确认；1->已确认(新创建的订单应该是未收货状态)
        omsOrder.setConfirmStatus(0);
        //订单类型（默认为普通订单）
        omsOrder.setPayType(0);
        // 订单用户名
        //订单详细地址信息
        omsOrder.setReceiverPostCode(umsMemberReceiveAddress.getPostCode());
        omsOrder.setReceiverProvince(umsMemberReceiveAddress.getProvince());
        omsOrder.setReceiverCity(umsMemberReceiveAddress.getCity());
        omsOrder.setReceiverRegion(umsMemberReceiveAddress.getRegion());
        omsOrder.setReceiverDetailAddress(umsMemberReceiveAddress.getDetailAddress());

        //    计算该订单内的totalAmount，payAmount，freightAmount，promotionAmount，integrationAmount，couponAmount，discountAmount
        OrderConfirmDTO orderConfirmDTO = new OrderConfirmDTO();
        List<OmsCartItem> cartList = new ArrayList<>();
        for (CartItemsDTO cartItemsDTOById : cartItemsDTOByIds) {
            cartList.add(cartItemsDTOById);
        }
        // 订单来源：0->PC订单；1->app订单(默认为pc订单)
        //    订单编号
        omsOrder.setOrderSn(generateOrderSn(omsOrder));
        //新创建的订单为未支付状态，默认支付方式为0
        omsOrder.setPayType(0);
        orderConfirmDTO.setCartList(cartList);
        // countAmounts(orderConfirmDTO);
        orderConfirmDTO.setCartList(cartList);
        omsOrder.setTotalAmount(orderConfirmDTO.getPriceTotal());
        omsOrder.setPayAmount(orderConfirmDTO.getPayAmount());
        //优惠券
        SmsCoupon smsCouponById = smsCouponService.getById(generateOrderDTO.getCouponId());
        return omsOrder;

    }

/*
    private void countAmounts(OrderConfirmDTO orderConfirmDTO) {
        //购物车内商品的总数量
        Integer cartItemTotalCount = 0;
        //订单所包含的商品总额
        BigDecimal priceTotal = new BigDecimal(0);
        //订单所包含的商品总运费
        BigDecimal freightAmount = new BigDecimal(0);
        //订单总支付金额
        BigDecimal payAmount = new BigDecimal(0);
        List<OmsCartItem> confirmDTOCartList = orderConfirmDTO.getCartList();
        for (OmsCartItem omsCartItem : confirmDTOCartList) {
            PmsSkuStock pmsSkuStock = pmsSkuStockService.getById(omsCartItem.getProductSkuId());
            Integer eachProductQuantity = 0;
            cartItemTotalCount += eachProductQuantity;
            eachProductQuantity = omsCartItem.getQuantity();
            priceTotal = priceTotal.add(pmsSkuStock.getPrice().multiply(new BigDecimal(eachProductQuantity)));
            PmsProduct pmsProduct = pmsProductService.getById(omsCartItem.getProductId());
            //获取该种商品的服务类型
            String serviceIds = pmsProduct.getServiceIds();
            if (serviceIds != null) {
                String[] services = serviceIds.split(",");
                if (ArrayUtil.containsAny(services, "3")) {
                    //    如果包含包邮服务则直接无需添加该商品的邮费
                    continue;
                }
            }
            //若该商品无三包服务，或者三包服务内无包邮服务，则添加邮费
            PmsFreightTemplate pmsFreightTemplate = pmsFreightTemplateService.getById(pmsProduct.getFreightTemplateId());
            freightAmount = freightAmount.add(pmsFreightTemplate.getFirstFee());
        }
        //    实际支付金额=priceTotal+freightAmount
        payAmount = priceTotal.add(freightAmount);

        orderConfirmDTO.setProductTotal(cartItemTotalCount);
        orderConfirmDTO.setPayAmount(payAmount);
        orderConfirmDTO.setPriceTotal(priceTotal);
        orderConfirmDTO.setFreightAmount(freightAmount);

    }
*/


    private String hasSkuStock(List<CartItemsDTO> cartItemsDTOByIds) {
        for (CartItemsDTO cartItemsDTOById : cartItemsDTOByIds) {
            //如果某一商品的实际库存<欲购买数量
            if (cartItemsDTOById.getStock() < cartItemsDTOById.getQuantity()) {
                return cartItemsDTOById.getProductName();
            }
        }
        return null;
    }

    /**
     * 根据order_id查找出相应的order_items_ids list集合
     *
     * @param omsOrder
     * @return
     */
    // private List<Long> getOrderItemsIdList(OmsOrder omsOrder) {
    //     LambdaQueryWrapper<OmsOrderItem> orderItemLambdaQueryWrapper = new QueryWrapper<OmsOrderItem>().lambda();
    //     orderItemLambdaQueryWrapper.eq(OmsOrderItem::getOrderId, omsOrder.getId());
    //     // List<OmsOrderItem> omsOrderItemList = omsOrderItemService.list(orderItemLambdaQueryWrapper);
    //     // List<Long> omsOrderItemsList = omsOrderItemList.stream().map(OmsOrderItem::getId).collect(Collectors.toList());
    //     return omsOrderItemsList;
    // }

    /**
     * 生成订单编号：生成规则:8位日期+2位平台号码+6位以上自增id；
     *
     * @return
     */
    private String generateOrderSn(OmsOrder omsOrder) {
        // 订单编号
        StringBuilder sb = new StringBuilder();
        //8位日期
        String yyyyMMdd = new SimpleDateFormat("yyyyMMdd").format(new Date());
        sb.append(yyyyMMdd);
        //2位平台号码  1.pc  2.app
        //String.format：参数说
        // 0 代表前面补充零
        // 6 代表补充长度
        // d 代表正数
        // 6位以上自增id
        // redis incr  原子性
        // 适合并发的自增方式：
        String key = REDIS_KEY_PREFIX_ORDER_ID + yyyyMMdd;
        Long incr = redisService.incr(key, 1);
        // 拿到当前的6位以上自增 如果小于6位，自动补全0
        if (incr.toString().length() <= 6) {
            sb.append(String.format("%06d", redisService.incr(key, 1)));
        } else {
            // 如果是6位 不用补0
            sb.append(incr);
        }
        return sb.toString();

    }

}
