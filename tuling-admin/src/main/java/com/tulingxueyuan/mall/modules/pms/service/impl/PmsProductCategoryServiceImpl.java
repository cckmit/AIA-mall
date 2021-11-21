package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.dto.ProductCateChildrenDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductCategoryService;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductCategoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2021-09-04
 */
@Slf4j
@Service
public class PmsProductCategoryServiceImpl extends ServiceImpl<PmsProductCategoryMapper, PmsProductCategory> implements PmsProductCategoryService {
    @Autowired
    PmsProductCategoryMapper productCategoryMapper;

    @Override
    public boolean updateShowStatus(List<Long> ids, Integer showStatus) {
        UpdateWrapper<PmsProductCategory> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .set(PmsProductCategory::getShowStatus, showStatus)
                .in(PmsProductCategory::getId,ids);
        return this.update(updateWrapper);
    }

    @Override
    public List<ProductCateChildrenDTO> getWithChildren() {
        List<ProductCateChildrenDTO> withChildren = productCategoryMapper.getWithChildren();
        for (ProductCateChildrenDTO withChild : withChildren) {
            log.info(withChild.toString());
        }
        return withChildren;
    }

    @Override
    public Page getList(Long parentId, Integer pageNum, Integer pageSize) {

        Page page = new Page(pageNum, pageSize);
        QueryWrapper<PmsProductCategory> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PmsProductCategory::getParentId,parentId).orderByAsc(PmsProductCategory::getSort);
        return this.page(page,wrapper);

    }

    @Override
    public boolean updateNavStatus(List<Long> ids, Integer navStatus) {
        LambdaUpdateWrapper<PmsProductCategory> updateWrapper = new UpdateWrapper<PmsProductCategory>().lambda()
                .set(PmsProductCategory::getNavStatus, navStatus)
                .in(PmsProductCategory::getId, ids);
        return this.update(updateWrapper);
    }
}
