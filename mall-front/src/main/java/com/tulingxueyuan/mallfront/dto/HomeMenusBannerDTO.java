package com.tulingxueyuan.mallfront.dto;

import com.tulingxueyuan.mallfront.modules.sms.model.SmsHomeAdvertise;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/***
 * @Author peipei
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="首页类型导航栏和banner组合数据传输对象", description="首页类型导航栏和banner数据")
public class HomeMenusBannerDTO {

    private List<HomeMenusDTO> homeMenusList;
    private List<SmsHomeAdvertise> homeAdvertisesList;
}
