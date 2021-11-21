package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.dto.PmsProductDTO;
import com.tulingxueyuan.dto.PmsProductDetailDTO;
import com.tulingxueyuan.dto.PmsProductSaveDTO;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeValue;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductService;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 *
 * @author peipei
 * @since 2021-09-04
 */
@Slf4j
@RestController
@RequestMapping("/product")
public class PmsProductController {
    @Autowired
    PmsProductService productService;


    @ApiOperation("获得商品列表")
    @GetMapping("/list")
    public CommonResult getProductList(PmsProductDTO pmsProductDto) {
        Page list = productService.getList(pmsProductDto);
        if (list != null) {
            return CommonResult.success(CommonPage.restPage(list));
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("更新商品上架状态")
    @PostMapping("/update/publishStatus")
    @Transactional
    public CommonResult publishStatus(@RequestParam("ids") List<Long> ids,
                                      @RequestParam("publishStatus") Integer status) {

        boolean update = productService.updatePublishStatus(ids, status);
        if (update) {
            return CommonResult.success(update);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("更新商品是否为新品")
    @PostMapping("/update/newStatus")
    @Transactional
    public CommonResult updateNewStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("newStatus") Integer newStatus) {

        boolean update = productService.updateNewStatus(ids, newStatus);
        if (update) {
            return CommonResult.success(update);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("更新商品推荐状态")
    @PostMapping("/update/recommendStatus")
    @Transactional
    public CommonResult updateRecommendStatus(@RequestParam("ids") List<Long> ids,
                                              @RequestParam("recommendStatus") Integer recommendStatus) {

        boolean update = productService.updateRecommendStatus(ids, recommendStatus);
        if (update) {
            return CommonResult.success(update);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("删除指定商品")
    @PostMapping("/update/deleteStatus")
    @Transactional
    public CommonResult deleteStatus(@RequestParam("ids") List<Long> ids,
                                     @RequestParam("deleteStatus") Integer deleteStatus) {
        boolean update = productService.updateByIds(ids, deleteStatus);
        if (update) {
            return CommonResult.success(update);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("获得需要更新的商品的信息")
    @GetMapping("/updateInfo/{id}")
    public CommonResult updateInfo(@PathVariable("id") Long id) {
        PmsProductDetailDTO productDetailDTO = productService.getInfoById(id);
        log.info(productDetailDTO.getSkuStockList().toString());
        if (productDetailDTO != null) {
            return CommonResult.success(productDetailDTO);
        } else {
            return CommonResult.failed();
        }

    }


    @ApiOperation("更新商品信息")
    @PostMapping("/update/{id}")
    @Transactional
    public CommonResult updateProduct(@PathVariable("id") Long id,
                                      @RequestBody PmsProductDetailDTO pmsProductDetailDTO) {
        boolean update = productService.updateProduct(id,pmsProductDetailDTO);
        if (update) {
            return CommonResult.success(update);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("增添商品")
    @PostMapping("/create")
    @Transactional
    public CommonResult createProduct(@RequestBody PmsProductSaveDTO productSaveDTO) {
        boolean create = productService.create(productSaveDTO);
        if (create) {
            return CommonResult.success(create);
        } else {
            return CommonResult.failed();
        }
    }

}

