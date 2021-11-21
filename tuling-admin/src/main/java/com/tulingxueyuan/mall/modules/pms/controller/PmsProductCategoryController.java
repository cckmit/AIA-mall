package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.dto.ProductCateChildrenDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductCategoryService;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 产品分类 前端控制器
 * </p>
 *
 * @author peipei
 * @since 2021-09-04
 */
@Slf4j
@RestController
@RequestMapping("/productCategory")
public class PmsProductCategoryController {
    @Autowired
    PmsProductCategoryService productCategoryService;


    @ApiOperation("商品列表")
    @GetMapping("/list/{parentId}")
    public CommonResult<CommonPage<PmsProductCategory>> gentlest(@PathVariable("parentId") Long id,
                                                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                 @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        Page list = productCategoryService.getList(id, pageNum, pageSize);
        CommonResult success = CommonResult.success(CommonPage.restPage(list));
        return success;
    }

    @ApiOperation("导航栏状态")
    @PostMapping("/update/navStatus")
    public CommonResult updateNavStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("navStatus") Integer navStatus) {
        boolean update = productCategoryService.updateNavStatus(ids, navStatus);
        if (update) {
            return CommonResult.success(update);
        } else {
            return CommonResult.failed();
        }


    }

    @ApiOperation("显示状态")
    @PostMapping("/update/showStatus")
    public CommonResult updateShowStatus(@RequestParam("ids") List<Long> ids,
                                         @RequestParam("showStatus") Integer showStatus) {
        boolean update = productCategoryService.updateShowStatus(ids, showStatus);
        if (update) {
            return CommonResult.success(update);
        } else {
            return CommonResult.failed();
        }


    }

    @ApiOperation("删除商品")
    @PostMapping("/delete/{id}")
    public CommonResult deleteProduct(@PathVariable("id") Long id) {
        boolean remove = productCategoryService.removeById(id);
        if (remove) {
            return CommonResult.success(remove);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("")
    @GetMapping("list/withChildren")
    public CommonResult getWithChildren() {
        List<ProductCateChildrenDTO> withChildren = productCategoryService.getWithChildren();
        return CommonResult.success(withChildren);

    }


}

