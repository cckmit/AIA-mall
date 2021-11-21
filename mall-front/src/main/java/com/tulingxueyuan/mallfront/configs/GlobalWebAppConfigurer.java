package com.tulingxueyuan.mallfront.configs;

import com.tulingxueyuan.mallfront.component.TradePayProp;
import com.tulingxueyuan.mallfront.component.TradePayProp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 权限验证拦截器
 */
@Configuration
public class GlobalWebAppConfigurer extends WebMvcConfigurerAdapter  {

    @Autowired
    TradePayProp tradePayProp;




    // 将物理文件夹中的支付二维码映射为静态资源路径
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(tradePayProp.getHttpBasePath()+"/**")
                .addResourceLocations("file:"+tradePayProp.getStorePath()+"/");
        super.addResourceHandlers(registry);
    }
}
