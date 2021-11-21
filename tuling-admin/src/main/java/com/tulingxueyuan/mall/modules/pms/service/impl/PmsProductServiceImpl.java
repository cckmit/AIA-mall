package com.tulingxueyuan.mall.modules.pms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.tulingxueyuan.dto.PmsProductDTO;
import com.tulingxueyuan.dto.PmsProductDetailDTO;
import com.tulingxueyuan.dto.PmsProductSaveDTO;
import com.tulingxueyuan.mall.modules.pms.model.PmsMemberPrice;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeValue;
import com.tulingxueyuan.mall.modules.pms.service.*;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2021-09-04
 */
@Slf4j
@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements PmsProductService {
    @Autowired
    PmsProductMapper productMapper;

    @Autowired
    PmsMemberPriceService memberPriceService;

    @Autowired
    PmsProductLadderService productLadderService;

    @Autowired
    PmsProductFullReductionService productFullReductionService;

    @Autowired
    PmsSkuStockService skuStockService;

    @Autowired
    PmsProductAttributeValueService productAttributeValueService;

    @Override
    public boolean create(PmsProductSaveDTO productSaveDto) {
        PmsProduct product = productSaveDto;
        product.setId(null);
        boolean result = this.save(product);
        if (result) {
            // 为了解决 前端会传入其他促销方式的空数据进来
            switch (product.getPromotionType()) {
                case 2:
                    // 2. 会员价格

                    // 根据商品id删除
                    deletePromotionPrice(product.getId(), memberPriceService);
                    saveNewPromotionList(productSaveDto.getMemberPriceList(), product.getId(), memberPriceService);
                    break;
                case 3:
                    // 根据商品id删除
                    deletePromotionPrice(product.getId(), productLadderService);
                    // 3. 阶梯价格
                    saveNewPromotionList(productSaveDto.getProductLadderList(), product.getId(), productLadderService);
                    break;
                case 4:
                    // 根据商品id删除
                    deletePromotionPrice(product.getId(), productFullReductionService);
                    // 4. 减满价格
                    saveNewPromotionList(productSaveDto.getProductFullReductionList(), product.getId(), productFullReductionService);
                    break;
            }
            // 根据商品id删除
            deletePromotionPrice(product.getId(), skuStockService);
            // 5. sku
            saveNewPromotionList(productSaveDto.getSkuStockList(), product.getId(), skuStockService);

            // 根据商品id删除
            deletePromotionPrice(product.getId(), productAttributeValueService);
            // 6 spu
            saveNewPromotionList(productSaveDto.getProductAttributeValueList(), product.getId(), productAttributeValueService);

        }
        return result;
    }

    @Override
    public PmsProductDetailDTO getInfoById(Long id) {
        PmsProductDetailDTO productDetailDTO = productMapper.getInfoById(id);
        if (productDetailDTO != null) {
            return productDetailDTO;
        } else {
            return null;
        }
    }

    @Override
    public boolean updateProduct(Long id, PmsProductDetailDTO pmsProductDetailDTO) {
        PmsProduct product = pmsProductDetailDTO;
        boolean update = this.updateById(product);
        if (update) {
            //先删除原来的优惠策略
            Integer promotionType = product.getPromotionType();
            //TODO 特惠促销类型
           /* if (promotionType == 1) {
                deletePromotionPrice(id,)
            }*/
            //会员价格
            if (promotionType == 2) {
                deletePromotionPrice(id, memberPriceService);
                saveNewPromotionList(pmsProductDetailDTO.getMemberPriceList(), id, memberPriceService);
            }/*阶梯价格*/ else if (promotionType == 3) {
                deletePromotionPrice(id, productLadderService);
                saveNewPromotionList(pmsProductDetailDTO.getProductLadderList(), id, productLadderService);
            }/*满减价格*/ else if (promotionType == 4) {
                deletePromotionPrice(id, productFullReductionService);
                saveNewPromotionList(pmsProductDetailDTO.getProductFullReductionList(), id, productFullReductionService);
            }
        } else {
            return false;
        }
        return true;
    }

    private void saveNewPromotionList(List list, Long id, IService service) {
        for (Object object : list) {
            try {
                Method setProductId = object.getClass().getMethod("setProductId", Long.class);
                setProductId.invoke(object, id);
                Method getId = object.getClass().getMethod("getId", Long.class);
                getId.invoke(object, (Long) null);
                service.save(object);
            } catch (Exception e) {

            }
        }
    }

    private void deletePromotionPrice(Long id, IService memberPriceService) {
        LambdaQueryWrapper<PmsMemberPrice> lambdaQueryWrapper = new QueryWrapper<PmsMemberPrice>().lambda();
        lambdaQueryWrapper.eq(PmsMemberPrice::getProductId, id);
        memberPriceService.remove(lambdaQueryWrapper);
    }

    @Override
    public Page getList(PmsProductDTO pmsProductDto) {
        Page page = new Page(pmsProductDto.getPageNum(), pmsProductDto.getPageSize());
        LambdaQueryWrapper<PmsProduct> lambdaQueryWrapper = new QueryWrapper<PmsProduct>().lambda();
        //商品名称
        if (StrUtil.isNotBlank(pmsProductDto.getKeyword())) {
            lambdaQueryWrapper.like(PmsProduct::getName, pmsProductDto.getKeyword());
        }
        //商品货号
        if (StrUtil.isNotBlank(pmsProductDto.getProductSn())) {
            lambdaQueryWrapper.like(PmsProduct::getProductSn, pmsProductDto.getProductSn());
        }
        // 商品分类
        if (pmsProductDto.getProductCategoryId() != null && pmsProductDto.getProductCategoryId() > 0) {
            lambdaQueryWrapper.eq(PmsProduct::getProductCategoryId, pmsProductDto.getProductCategoryId());
        }
        // 商品品牌
        if (pmsProductDto.getBrandId() != null && pmsProductDto.getBrandId() > 0) {
            lambdaQueryWrapper.eq(PmsProduct::getBrandId, pmsProductDto.getBrandId());
        }
        // 上架状态
        if (pmsProductDto.getPublishStatus() != null) {
            lambdaQueryWrapper.eq(PmsProduct::getPublishStatus, pmsProductDto.getPublishStatus());
        }
        // 审核状态
        if (pmsProductDto.getVerifyStatus() != null) {
            lambdaQueryWrapper.eq(PmsProduct::getVerifyStatus, pmsProductDto.getVerifyStatus());
        }
        lambdaQueryWrapper.eq(PmsProduct::getDeleteStatus,pmsProductDto.getDeleteStatus()).orderByAsc(PmsProduct::getId);
        return this.page(page, lambdaQueryWrapper);

    }

    @Override
    public boolean updatePublishStatus(List<Long> ids, Integer status) {
        boolean update = true;
        List<PmsProduct> pmsProducts = productMapper.selectBatchIds(ids);
        LambdaUpdateWrapper<PmsProduct> updateWrapper = new UpdateWrapper<PmsProduct>().lambda();
        updateWrapper.in(PmsProduct::getId, ids);
        pmsProducts.forEach(p -> {
            if (p.getPublishStatus() != status) {
                p.setPublishStatus(status);
                this.updateById(p);
            }
        });
        return update;
    }

    @Override
    public boolean updateNewStatus(List<Long> ids, Integer newStatus) {
        boolean update = true;
        List<PmsProduct> pmsProducts = productMapper.selectBatchIds(ids);
        LambdaUpdateWrapper<PmsProduct> updateWrapper = new UpdateWrapper<PmsProduct>().lambda();
        updateWrapper.in(PmsProduct::getId, ids);
        pmsProducts.forEach(p -> {
            if (p.getPublishStatus() != newStatus) {
                p.setPublishStatus(newStatus);
                this.updateById(p);
            }
        });
        return update;
    }

    @Override
    public boolean updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        List<PmsProduct> pmsProducts = productMapper.selectBatchIds(ids);
        LambdaUpdateWrapper<PmsProduct> updateWrapper = new UpdateWrapper<PmsProduct>().lambda();
        updateWrapper.in(PmsProduct::getId, ids);
        pmsProducts.forEach(p -> {
            if (p.getPublishStatus() != recommendStatus) {
                p.setPublishStatus(recommendStatus);
                this.updateById(p);
            }
        });
        for (int i = 0; i < pmsProducts.size(); i++) {
            if (pmsProducts.get(i).getRecommandStatus() != recommendStatus) {
                pmsProducts.get(i).setRecommandStatus(recommendStatus);
            }
        }
        boolean update = this.updateBatchById(pmsProducts);
        return update;

    }

    @Override
    public boolean updateByIds(List<Long> ids, Integer deleteStatus) {
        boolean update=true;
        List<PmsProduct> pmsProductList = productMapper.selectBatchIds(ids);
        //如果deleteStatus==0则将其放入回收站,否则永久删除
        if (deleteStatus == 0) {
            for (int i = 0; i < pmsProductList.size(); i++) {
                pmsProductList.get(i).setDeleteStatus(1);
            }
            update=this.updateBatchById(pmsProductList);
        } else {
            update=this.removeByIds(ids);
        }
        return update;
    }

}
