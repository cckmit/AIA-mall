package com.tulingxueyuan.mallfront.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/***
 * @Author peipei
 */
@Data
@Component
@ConfigurationProperties(prefix = "trade.alipay.qrcode")
public class TradePayProp {

   private String alipaySuccessFallBack;
   private String wechatPaySuccessFallBack;
   private String storePath;
   private String httpBasePath;
}
