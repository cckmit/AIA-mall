package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 前端控制器
 * </p>
 *
 * @author peipei
 * @since 2021-09-04
 */
@Slf4j
@RestController
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {

    @Autowired
    PmsProductAttributeService productAttributeService;

    @ApiOperation("属性/参数列表")
    @GetMapping("/list/{attCateId}")
    @Transactional
    public CommonResult<CommonPage<PmsProductAttribute>> getList(@PathVariable(value = "attCateId") Long attCateId,
                                                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                 @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                                 @RequestParam(value = "type") Integer type) {
        Page list = productAttributeService.getList(attCateId, pageNum, pageSize, type);
        CommonResult result = CommonResult.success(CommonPage.restPage(list));
        return result;
    }


    @ApiOperation("添加属性/参数")
    @PostMapping("/create")
    public CommonResult createAttr(@RequestBody PmsProductAttribute productAttribute) {
        boolean save = productAttributeService.create(productAttribute);
        if (save) {
            return CommonResult.success(save);
        } else {
            return CommonResult.failed();
        }

    }

    @ApiOperation("删除属性/参数")
    @PostMapping("/delete/{cid}/{type}")
    public CommonResult delete(@PathVariable("cid") Long cid,
                               @PathVariable("type") Integer type,
                               @RequestParam("ids") List<Long> ids) {
        boolean remove = productAttributeService.removes(cid,type, ids);
        if (remove) {
            return CommonResult.success(remove);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("得到需要更新的属性/参数")
    @GetMapping("/{id}")
    public CommonResult getAttr(@PathVariable("id") Long id) {
        PmsProductAttribute productAttribute = productAttributeService.getOne(new QueryWrapper<PmsProductAttribute>()
                .lambda()
                .eq(PmsProductAttribute::getId, id));
        if (productAttribute != null) {
            return CommonResult.success(productAttribute);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("更新需要属性/参数")
    @PostMapping("/update")
    public CommonResult updateAttr(@RequestBody PmsProductAttribute productAttribute) {
        boolean update = productAttributeService.updateById(productAttribute);
        if (update) {
            return CommonResult.success(update);
        } else {
            return CommonResult.failed();
        }

    }

}

