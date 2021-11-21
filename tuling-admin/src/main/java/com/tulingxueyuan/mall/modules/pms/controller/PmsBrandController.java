package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsBrand;
import com.tulingxueyuan.mall.modules.pms.service.PmsBrandService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author peipei
 * @since 2021-09-04
 */
@Slf4j
@RestController
@RequestMapping("/brand")
public class PmsBrandController {
    @Autowired
    PmsBrandService brandService;

    @ApiOperation("获取品牌列表")
    @GetMapping("/list")
    public CommonResult<CommonPage<PmsBrand>> getList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                      @RequestParam(value = "keyWord",defaultValue = "") String keyWord) {
        Page list = brandService.getList(pageNum, pageSize, keyWord);
        return CommonResult.success(CommonPage.restPage(list));

    }

    @ApiOperation("修改品牌显示状态")
    @PostMapping("/update/showStatus")
    public CommonResult updateShowStatus(@RequestParam("ids") Long id,
                                         @RequestParam("showStatus") Integer showStatus) {
        UpdateWrapper<PmsBrand> brandUpdateWrapper = new UpdateWrapper<>();
        if (showStatus == 1) {
            brandUpdateWrapper.lambda().eq(PmsBrand::getId, id).set(PmsBrand::getShowStatus, 1);
        } else {
            brandUpdateWrapper.lambda().eq(PmsBrand::getId, id).set(PmsBrand::getShowStatus, 0);
        }
        boolean update = brandService.update(brandUpdateWrapper);
        if (update) {
            return CommonResult.success(update);
        } else {
            return CommonResult.failed();
        }


    }

    @ApiOperation("修改品牌制造商显示状态")
    @PostMapping("/update/factoryStatus")
    public CommonResult updateFactoryStatus(@RequestParam("ids") Long id,
                                            @RequestParam("factoryStatus") Integer factoryStatus) {
        UpdateWrapper<PmsBrand> brandUpdateWrapper = new UpdateWrapper<>();
        if (factoryStatus == 1) {
            brandUpdateWrapper.lambda().eq(PmsBrand::getId, id).set(PmsBrand::getFactoryStatus, 1);
        } else {
            brandUpdateWrapper.lambda().eq(PmsBrand::getId, id).set(PmsBrand::getFactoryStatus, 0);
        }
        boolean update = brandService.update(brandUpdateWrapper);
        if (update) {
            return CommonResult.success(update);
        } else {
            return CommonResult.failed();
        }


    }


    @ApiOperation("删除品牌")
    @PostMapping("/delete/{id}")
    public CommonResult deleteBrand(@PathVariable("id") Long id) {
        boolean remove = brandService.removeById(id);
        if (remove) {
            return CommonResult.success(remove);
        } else {
            return CommonResult.failed();
        }

    }

    @ApiOperation("新建品牌类型")
    @PostMapping("/create")
    public CommonResult createBrand(PmsBrand pmsBrand) {
        boolean save = brandService.save(pmsBrand);
        if (save) {
            return CommonResult.success(save);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("获得品牌类型详情")
    @GetMapping("/get/{id}")
    public CommonResult getBrand(@PathVariable("id") Long id) {
        QueryWrapper<PmsBrand> brandQueryWrapper = new QueryWrapper<>();
        brandQueryWrapper.lambda().eq(PmsBrand::getId, id);
        PmsBrand pmsBrand = brandService.getOne(brandQueryWrapper);
        if (pmsBrand != null) {
            return CommonResult.success(pmsBrand);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("更新品牌类型")
    @PostMapping("/update/{id}")
    public CommonResult updateBrand(@PathVariable("id") Long id,@RequestBody PmsBrand pmsBrand) {
        boolean save = brandService.save(pmsBrand);
        if (save) {
            return CommonResult.success(save);
        } else {
            return CommonResult.failed();
        }
    }


}

