package com.tulingxueyuan.mall.modules.pms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.modules.pms.model.PmsBrand;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author peipei
 * @since 2021-09-04
 */
public interface PmsBrandService extends IService<PmsBrand> {

    Page getList(Integer pageNum,Integer pageSize,String keyWord);
}
