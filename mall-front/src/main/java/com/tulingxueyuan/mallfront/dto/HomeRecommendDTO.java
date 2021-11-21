package com.tulingxueyuan.mallfront.dto;

import com.tulingxueyuan.mallfront.modules.pms.model.PmsProduct;
import io.swagger.annotations.Api;
import lombok.Data;

import java.util.List;
import java.util.StringTokenizer;

/**
 * @Description:
 * @Name: HomeRecommendDTO
 * @Author peipei
 * @Date 2021/10/21
 */
@Api("首页推荐商品列表")
@Data
public class HomeRecommendDTO {
    private String pic;

    private String url;

    private String categoryName;

    private List<ProductDTO> productList;

}
