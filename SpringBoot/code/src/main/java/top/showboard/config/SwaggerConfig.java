package top.showboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: Erhuoduoduo_Platform_Springboot_System
 * @description: Swagger配置类
 * @author: collapsar
 * @create: 2021/11/30 21:06
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket getDocket(){

        //创建封面信息
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        apiInfoBuilder.title("《展示看板》接口说明文档")
                .description("此文档详细说明了《展示看板》项目后端接口规范")
                .version("v1.0.0")
                .contact( new Contact("毕金翔","www.collapsar.top","31901224@stu.zucc.edu.cn"));

        ApiInfo apiInfo = apiInfoBuilder.build();

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.showboard.controller"))
                .paths(PathSelectors.any())
                .build();

        return docket;
    }
}
