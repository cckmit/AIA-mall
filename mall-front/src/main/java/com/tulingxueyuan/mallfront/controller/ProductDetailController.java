package com.tulingxueyuan.mallfront.controller;

import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mallfront.dto.ProductDetailDTO;
import com.tulingxueyuan.mallfront.modules.pms.service.PmsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @Name: ProductDetailController
 * @Author peipei
 * @Date 2021/10/16
 */
@RestController
@RequestMapping("/product")
public class ProductDetailController {
    @Autowired
    PmsProductService productService;

}
