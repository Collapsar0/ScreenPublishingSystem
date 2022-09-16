package top.showboard;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: showboard
 * @description: springboot启动类
 * @author: collapsar
 * @create: 2022/06/24 13:52
 */
@MapperScan("top.showboard.mapper")
//开启swaggerUi
@EnableSwagger2
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}

