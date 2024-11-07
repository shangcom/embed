package hello.spring;

import org.springframework.context.annotation.Bean;

/*
MySpringBootApplication 어노테이션에 @ComponentScan 포함되어 있으므로 @Configuration 붙이지 않아도 자동 스캔 대상임.
 */
//@Configuration
public class HelloConfig {

    @Bean
    public HelloController helloController() {
        return new HelloController();
    }
}
