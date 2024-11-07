package hello.boot;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.List;

public class MySpringApplication {
    /**
     * static으로 지정했기 때문에 클래스 이름을 통해 직접 호출 가능.
     * 즉, 스프링 부트 애플리케이션의 진입점에서 애플리케이션을 초기화하고 실항하기 위해 사용.
     *
     * @param configClass 스프링 컨테이너에 넣을 config 클래스(스프링 설정)
     * @param args        전달받아 사용.
     */
    public static void run(Class configClass, String[] args) {
        System.out.println("[MySpringApplication.main] args = " + List.of(args));

        // 내부 톰캣 생성
        Tomcat tomcat = new Tomcat();

        Connector connector = new Connector();
        connector.setPort(8080);
        tomcat.setConnector(connector);

        // 스프링 컨테이너 생성
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(configClass);

        // 디스패처 서블릿 생성
        DispatcherServlet dispatcher = new DispatcherServlet(appContext);

        // 컨텍스트 설정
        Context context = tomcat.addContext("", "/");

        tomcat.addServlet("", "dispatcher", dispatcher);
        context.addServletMappingDecoded("/", "dispatcher");

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }
}
