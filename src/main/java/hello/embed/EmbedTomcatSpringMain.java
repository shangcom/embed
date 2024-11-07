package hello.embed;


import hello.spring.HelloConfig;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;

/**
 * 스프링 MVC 기반.
 * 스프링의 DispatcherServlet을 서블릿으로 등록하여 MVC 구조를 구현.
 * 컨트롤러와 함께 작동하도록 스프링의 설정을 적용.
 * 구조:
 * - 서블릿 컨테이너 → DispatcherServlet → 스프링 컨테이너
 *   (서블릿 컨테이너는 DispatcherServlet을, DispatcherServlet은 스프링 컨테이너를,
 *   스프링 컨테이너는 HelloConfig.class를 알고 있음)
 * 주요 역할:
 * - 서블릿 컨테이너: DispatcherServlet을 구동하여 요청을 스프링 컨테이너로 전달
 * - DispatcherServlet: 스프링 MVC의 진입점으로서 컨트롤러 호출을 위임
 * - 스프링 컨테이너: @Controller 빈 관리 및 설정을 담당 (예: HelloConfig.class)
 */
public class EmbedTomcatSpringMain {
    public static void main(String[] args) throws LifecycleException {
        System.out.println("EmbedTomcatServletMain.main");

        // 내부 톰캣 생성
        Tomcat tomcat = new Tomcat();

        Connector connector = new Connector();
        connector.setPort(8080);
        tomcat.setConnector(connector);

        // 스프링 컨테이너 생성
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(HelloConfig.class);

        // 디스패처 서블릿 생성
        DispatcherServlet dispatcher = new DispatcherServlet(appContext);

        // 컨텍스트 설정
        Context context = tomcat.addContext("", "/");

        /*
         LifecycleException 발생 시 추가
         인텔리제이에서의 실행: 프로젝트 루트를 기준으로 기본 경로가 자동으로 설정되어 문제가 발생하지 않음.
         JAR 실행 시 문제: JAR 내부에서 경로를 찾지 못하기 때문에 명확히 경로를 설정해 줘야 함.
         */
        File docBaseFile = new File(context.getDocBase());
        if (!docBaseFile.isAbsolute()) {
            docBaseFile = new File(((org.apache.catalina.Host)
                    context.getParent()).getAppBaseFile(), docBaseFile.getPath());
        }
        docBaseFile.mkdirs();
        // 추가 끝

        tomcat.addServlet("", "dispatcher", dispatcher);
        context.addServletMappingDecoded("/", "dispatcher");

        tomcat.start();

    }
}
