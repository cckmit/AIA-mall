package com.tulingxueyuan.mallfront.configs;

import com.tulingxueyuan.mall.common.config.BaseSwaggerConfig;
import com.tulingxueyuan.mall.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger API文档相关配置
 *
 * @author peipei
 * @date 2021/9/3
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.tulingxueyuan.mall.modules")
                .title("mall-tiny基础版项目前台")
                .description("tuling_mall项目前台接口文档")
                .contactName("peipei")
                .version("1.0")
                .enableSecurity(false)
                .build();
    }
}
