package com.tulingxueyuan.mallfront.controller;

import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mallfront.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mallfront.modules.pms.service.PmsProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @Name: SearchResultController
 * @Author peipei
 * @Date 2021/10/14
 */
@Slf4j
@RestController
public class SearchResultController {
    @Autowired
    PmsProductService productService;


}
