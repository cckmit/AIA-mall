package com.tulingxueyuan.mallfront.modules.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tulingxueyuan.mallfront.dto.ADProductDTO;
import com.tulingxueyuan.mallfront.dto.ProductDetailDTO;
import com.tulingxueyuan.mallfront.modules.oms.model.OmsOrder;
import com.tulingxueyuan.mallfront.modules.oms.service.OmsOrderService;
import com.tulingxueyuan.mallfront.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mallfront.modules.pms.mapper.PmsProductMapper;
import com.tulingxueyuan.mallfront.modules.pms.service.PmsProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2021-03-14
 */
@Slf4j
@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements PmsProductService {
    @Autowired
    PmsProductMapper productMapper;

    @Autowired
    OmsOrderService omsOrderService;


    /*@Override
    public ProductDetailDTO getProductDetail(Long id) {
        ProductDetailDTO productDetailDTO = productMapper.getProductDetail(id);
        // log.info("productDetailDTO {}",productDetailDTO.toString());
        System.out.println(productDetailDTO.toString());
        return productDetailDTO;
    }*/

    @Override
    public List<ADProductDTO> getOrderCompleteLikes(Integer orderId) {
        OmsOrder orderById = omsOrderService.getById(orderId);
        PmsProduct productById = this.getById(orderById.getProductId());
        LambdaQueryWrapper<PmsProduct> pmsProductLambdaQueryWrapper = new QueryWrapper<PmsProduct>().lambda();
        pmsProductLambdaQueryWrapper.eq(PmsProduct::getProductCategoryId, productById.getProductCategoryId());
        List<PmsProduct> productList = this.list(pmsProductLambdaQueryWrapper);
        ArrayList<ADProductDTO> adProductDTOList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int randomIndex = random.nextInt(productList.size());
            PmsProduct pmsProduct = productList.get(randomIndex);
            ADProductDTO adProductDTO = new ADProductDTO();
            adProductDTO.setADProductId(pmsProduct.getId());
            adProductDTO.setADProductPic(pmsProduct.getPic());
            adProductDTO.setADProductName(pmsProduct.getName());
            adProductDTO.setADProductPrice(pmsProduct.getPrice());
            adProductDTOList.add(adProductDTO);
        }
        return adProductDTOList;

    }
}
