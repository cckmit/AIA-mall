package com.tulingxueyuan.mallfront.controller;

import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mallfront.dto.CartItemsDTO;
import com.tulingxueyuan.mallfront.dto.CartDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @Name: ShopCarController
 * @Author peipei
 * @Date 2021/10/17
 */
@Slf4j
@RestController
@RequestMapping("/car")
public class CartController {
/*
    @Autowired
    OmsCartItemService cartItemService;

    @PostMapping("/add")
    public CommonResult addProductToCar(@RequestBody CartDTO cartDTO) {
        boolean addProductToCar = cartItemService.addProductToCar(cartDTO);
        if (addProductToCar) {
            return CommonResult.success(addProductToCar);
        } else {
            return CommonResult.failed();
        }
    }

    @GetMapping("/products/sum")
    public CommonResult getCartProductCount() {
        Integer cartProductCount = cartItemService.getCartProductCount();
        return CommonResult.success(cartProductCount);
    }

    @GetMapping("/list")
    public CommonResult getCartList() {
        List<CartItemsDTO> cartProductList = cartItemService.getCartList();
        return CommonResult.success(cartProductList);
    }

    @PostMapping("/update/quantity")
    public CommonResult updateQuantity(@RequestParam("id") Long cartItemId,
                                       @RequestParam("quantity") Integer quantity) {
        boolean updateQuantity=cartItemService.updateQuantity(cartItemId,quantity);
        return CommonResult.success(updateQuantity);
    }

    @PostMapping("/delete")
    public CommonResult deleteCartItem(@RequestParam("ids") Long cartItemId) {
        boolean deleteCartItem = cartItemService.deleteCartItem(cartItemId);
        return CommonResult.success(deleteCartItem);
    }
*/



}
