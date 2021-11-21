package com.tulingxueyuan.mallfront.modules.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tulingxueyuan.mallfront.modules.pms.mapper.PmsProductAttributeMapper;
import com.tulingxueyuan.mallfront.modules.pms.mapper.PmsProductCategoryMapper;
import com.tulingxueyuan.mallfront.modules.pms.model.PmsProductAttribute;
import com.tulingxueyuan.mallfront.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mallfront.dto.HomeMenusDTO;
import com.tulingxueyuan.mallfront.modules.pms.service.PmsProductAttributeService;
import com.tulingxueyuan.mallfront.modules.pms.service.PmsProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2021-03-14
 */
@Service
public class PmsProductCategoryServiceImpl extends ServiceImpl<PmsProductCategoryMapper, PmsProductCategory> implements PmsProductCategoryService {
    @Autowired
    PmsProductCategoryMapper productCategoryMapper;

    @Autowired
    PmsProductAttributeService productAttributeService;

    @Autowired
    PmsProductAttributeMapper productAttributeMapper;

    @Override
    public List<HomeMenusDTO> getMenus() {
        List<HomeMenusDTO> menus = productCategoryMapper.getMenus();
        return menus;
    }

    @Override
    public String getAttrs(Long productCategoryId) {
        StringBuilder attries = new StringBuilder();
        LambdaQueryWrapper<PmsProductAttribute> productAttributeLambdaQueryWrapper = new QueryWrapper<PmsProductAttribute>().lambda();
        productAttributeLambdaQueryWrapper.eq(PmsProductAttribute::getProductAttributeCategoryId, productCategoryId);
        List<PmsProductAttribute> productAttributes = productAttributeMapper.selectList(productAttributeLambdaQueryWrapper);
        for (PmsProductAttribute productAttribute : productAttributes) {
            attries.append(productAttribute.getName());
        }
        return attries.toString();
    }
}
