package com.tulingxueyuan.mallfront.controller;

import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mallfront.dto.CartIdsDTO;
import com.tulingxueyuan.mallfront.dto.CartItemsDTO;
import com.tulingxueyuan.mallfront.modules.oms.service.OmsCartItemService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
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
@RequestMapping("/cart")
public class CartController {
    @Autowired
    OmsCartItemService cartItemService;

    @PostMapping("/update/ProductQuantity")
    public CommonResult addProductToCar(@RequestParam("cartItemId") Long cartItemId,
                                        @RequestParam("productSkuId") Long productSkuId,
                                        @RequestParam("quantity") Integer quantity) {
        boolean updateProductQuantity = cartItemService.updateProductQuantity(cartItemId, productSkuId, quantity);
        if (updateProductQuantity) {
            return CommonResult.success(updateProductQuantity);
        } else {
            return CommonResult.failed();
        }
    }


    @GetMapping("/list")
    public CommonResult getCartList() {
        List<CartItemsDTO> cartProductList = cartItemService.getCartList();
        return CommonResult.success(cartProductList);
    }

    @PostMapping("/delete")
    public CommonResult deleteCartItem(@RequestBody CartIdsDTO cartIdsDTO) {
        boolean deleteCartItem = cartItemService.deleteCartItem(cartIdsDTO.getIds());
        if (deleteCartItem) {
            return CommonResult.success(deleteCartItem);
        } else {
            return CommonResult.failed();
        }
    }
    //gitTest


    @PostMapping("/update/starStatus")
    public CommonResult starProduct(@RequestBody CartIdsDTO cartIdsDTO) {
        boolean starProduct = cartItemService.starCartProduct(cartIdsDTO);
        if (starProduct) {
            return CommonResult.success(starProduct);
        } else {
            return CommonResult.failed();
        }
    }


}
