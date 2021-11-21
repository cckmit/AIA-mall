package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeCategoryService;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeService;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductAttributeMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeCategory;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2021-09-04
 */
@Slf4j
@Service
public class PmsProductAttributeServiceImpl extends ServiceImpl<PmsProductAttributeMapper, PmsProductAttribute> implements PmsProductAttributeService {
    @Autowired
    PmsProductAttributeCategoryService productAttributeCategoryService;

    @Autowired
    PmsProductAttributeMapper attributeMapper;

    @Autowired
    PmsProductService productService;

    @Override
    public Page getList(Long attCateId, Integer pageNum, Integer pageSize, Integer type) {
        Page page = new Page(pageNum, pageSize);
        QueryWrapper<PmsProductAttribute> productAttributeQueryWrapper = new QueryWrapper<>();
        productAttributeQueryWrapper.lambda()
                .eq(PmsProductAttribute::getProductAttributeCategoryId, attCateId)
                .eq(PmsProductAttribute::getType, type)
                .orderByAsc(PmsProductAttribute::getSort);
        return this.page(page, productAttributeQueryWrapper);

    }




    @Override
    public boolean create(PmsProductAttribute productAttribute) {
        boolean save = this.save(productAttribute);
        UpdateWrapper<PmsProductAttributeCategory> productAttributeUpdateWrapper = new UpdateWrapper<>();
        if (save) {
            if (productAttribute.getType() == 0) {
                productAttributeUpdateWrapper.setSql("attribute_count=attribute_count+1");
            } else {
                productAttributeUpdateWrapper.setSql("param_count=param_count+1");
            }
            productAttributeUpdateWrapper
                    .lambda().eq(PmsProductAttributeCategory::getId, productAttribute.getProductAttributeCategoryId());
            boolean update = productAttributeCategoryService.update(productAttributeUpdateWrapper);
            if (update) {
                return true;
            } else {
                return false;
            }
        }
        return false;

    }

    @Override
    public boolean removes(Long cid, Integer type, List<Long> ids) {
        boolean remove = this.removeByIds(ids);
        if (remove) {
            UpdateWrapper<PmsProductAttributeCategory> attributeCategoryUpdateWrapper = new UpdateWrapper<>();
            if (type == 0) {
                attributeCategoryUpdateWrapper
                        .lambda().setSql("attribute_count=attribute_count-" + ids.size()).eq(PmsProductAttributeCategory::getId, cid);
            } else {
                attributeCategoryUpdateWrapper
                        .lambda().setSql("param_count=param_count-" + ids.size()).eq(PmsProductAttributeCategory::getId, cid);
            }
            productAttributeCategoryService.update(attributeCategoryUpdateWrapper);
        }
        return remove;

    }
}




