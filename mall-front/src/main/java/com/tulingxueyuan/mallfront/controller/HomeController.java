package com.tulingxueyuan.mallfront.controller;

import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mallfront.dto.HomeMenusBannerDTO;
import com.tulingxueyuan.mallfront.dto.HomeMenusDTO;
import com.tulingxueyuan.mallfront.dto.HomeRecommendDTO;
import com.tulingxueyuan.mallfront.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mallfront.modules.pms.service.PmsProductCategoryService;
import com.tulingxueyuan.mallfront.modules.sms.model.SmsHomeAdvertise;
import com.tulingxueyuan.mallfront.modules.sms.service.SmsHomeAdvertiseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.util.List;

/**
 * @Description:
 * @Name: HomeMenusController
 * @Author peipei
 * @Date 2021/10/14
 */
@Slf4j
@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    PmsProductCategoryService productCategoryService;

    @Autowired
    SmsHomeAdvertiseService homeAdvertiseService;

    @GetMapping("/menus")
    public CommonResult getMenus() {
        List<HomeMenusDTO> menusList = productCategoryService.getMenus();
        List<SmsHomeAdvertise> homeAdvertiseList = homeAdvertiseService.getAdverties();
        HomeMenusBannerDTO homeMenusBannerDTO = new HomeMenusBannerDTO();
        homeMenusBannerDTO.setHomeMenusList(menusList);
        homeMenusBannerDTO.setHomeAdvertisesList(homeAdvertiseList);
        if (homeMenusBannerDTO != null) {
            return CommonResult.success(homeMenusBannerDTO);
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * this.axios.get("/home/goods_sale")
     */
    @GetMapping("/goods_sale")
    public CommonResult getGoodSale() {
        List<HomeRecommendDTO> goodSale = homeAdvertiseService.getGoodSale();
        return CommonResult.success(goodSale);
    }
}
