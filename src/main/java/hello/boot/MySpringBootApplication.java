package hello.boot;

import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented // 해당 애노테이션이 JavaDoc에 포함될 수 있도록 지정하는 메타-애노테이션
@ComponentScan // 이 애노테이션을 사용하는 클래스의 패키지를 기준으로 하위 패키지들에서 스프링 빈을 자동으로 스캔하여 등록
public @interface MySpringBootApplication {
}
