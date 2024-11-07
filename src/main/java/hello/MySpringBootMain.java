package hello;

import hello.boot.MySpringApplication;
import hello.boot.MySpringBootApplication;

/**
 * '@Target(ElementType.TYPE)'
 * '@Retention(RetentionPolicy.RUNTIME)'
 * '@Documented'
 * '@ComponentScan'
 * 이렇게 네 개의 어노테이션으로 구성된다.
 * 현재 클래스, MySpringBootMain에 @Bean이나 설정이 없더라도, @MySpringBootApplication에 포함된
 * '@ComponentScan'이 패키지를 스캔하면서 필요한 빈과 설정을 찾아서 자동으로 스프링 컨테이너에 등록한다.
 * 즉 @Configuration이 붙어있지 않아도 configClass로서의 기능을 한다.
 */
@MySpringBootApplication
public class MySpringBootMain {
    public static void main(String[] args) {
        System.out.println("MySpringBootMain.main");
        MySpringApplication.run(MySpringBootMain.class, args);
    }
}
