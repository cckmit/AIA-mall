package com.tulingxueyuan.mall.modules.pms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.dto.PmsProductDTO;
import com.tulingxueyuan.dto.PmsProductDetailDTO;
import com.tulingxueyuan.dto.PmsProductSaveDTO;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author peipei
 * @since 2021-09-04
 */
public interface PmsProductService extends IService<PmsProduct> {

    Page getList(PmsProductDTO pmsProductDto);

    boolean updatePublishStatus(List<Long> ids,Integer status);

    boolean updateNewStatus(List<Long> ids, Integer newStatus);

    boolean updateRecommendStatus(List<Long> ids, Integer recommendStatus);


    boolean updateByIds(List<Long> ids, Integer deleteStatus);

    boolean create(PmsProductSaveDTO productSaveDTO);

    PmsProductDetailDTO getInfoById(Long id);

    boolean updateProduct(Long id, PmsProductDetailDTO pmsProductDetailDTO);
}
