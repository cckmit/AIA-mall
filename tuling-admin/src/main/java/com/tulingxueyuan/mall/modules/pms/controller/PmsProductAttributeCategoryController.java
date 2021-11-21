package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeCategory;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeCategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 产品属性分类表 前端控制器
 * </p>
 *
 * @author peipei
 * @since 2021-09-04
 */
@Slf4j
@RestController
@RequestMapping("/productAttribute/category")
public class PmsProductAttributeCategoryController {
    @Autowired
    PmsProductAttributeCategoryService productAttributeCategoryService;

    @ApiOperation("商品类型列表")
    @GetMapping("/list")
    public CommonResult<CommonPage<PmsProductAttribute>> getList(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                                 @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize) {

        Page list = productAttributeCategoryService.getList(pageNum, pageSize);
        CommonResult success = CommonResult.success(CommonPage.restPage(list));
        return success;
    }


    @ApiOperation("添加商品类型")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult createAttr(@RequestParam("name") String name) {
        PmsProductAttributeCategory productAttributeCategory = new PmsProductAttributeCategory();
        productAttributeCategory.setName(name);
        boolean save = productAttributeCategoryService.save(productAttributeCategory);
        if (save) {
          return CommonResult.success(save);
      } else {
          return CommonResult.failed();
      }


  }

    @ApiOperation("更新商品类型")
    @PostMapping("/update/{id}")
    public CommonResult updateAttr(@PathVariable("id") Long id,PmsProductAttributeCategory productAttributeCategory) {
        boolean update = productAttributeCategoryService.updateById(productAttributeCategory);
        if (update) {
            return CommonResult.success(update);
        }else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("删除商品类型")
    @PostMapping("/delete/{id}")
    public CommonResult deleteAttr(@PathVariable("id") Long id) {
        boolean remove = productAttributeCategoryService.removeById(id);
        if (remove) {
            return CommonResult.success(remove);
        }else {
            return CommonResult.failed();
        }
    }




}

