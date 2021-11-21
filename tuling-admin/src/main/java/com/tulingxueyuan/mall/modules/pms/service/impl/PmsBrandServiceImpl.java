package com.tulingxueyuan.mall.modules.pms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.modules.pms.model.PmsBrand;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsBrandMapper;
import com.tulingxueyuan.mall.modules.pms.service.PmsBrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2021-09-04
 */
@Service
public class PmsBrandServiceImpl extends ServiceImpl<PmsBrandMapper, PmsBrand> implements PmsBrandService {

    @Override
    public Page getList(Integer pageNum, Integer pageSize, String keyWord) {
        Page page = new Page(pageNum, pageSize);
        LambdaQueryWrapper<PmsBrand> lambdaQueryWrapper = new QueryWrapper<PmsBrand>().lambda();
        if (StrUtil.isNotEmpty(keyWord)) {
            lambdaQueryWrapper.like(PmsBrand::getName, keyWord);
        }
        lambdaQueryWrapper.orderByDesc(PmsBrand::getSort);
        return this.page(page,lambdaQueryWrapper);

    }
}
