package hello.embed;

import hello.servlet.HelloServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;


/**
 * 스프링 없는 순수 서블릿 환경.
 * 톰캣에 직접 서블릿 추가
 * 톰캣의 Context를 수동으로 설정하여 서블릿 등록.
 * HelloConfig(컨트롤러를 빈으로 등록) 사용 안한다.
 */
public class EmbedTomcatServletMain {
    public static void main(String[] args) throws LifecycleException {
        System.out.println("EmbedTomcatServletMain.main");

        /*
         톰캣 인스턴스 생성.
         외부 설정 없이 코드 내에서 톰캣 서버 시작할 수 있음.
         */
        Tomcat tomcat = new Tomcat();

        // 커넥터 생성 및 포트 설정. 톰캣 인스턴스에 커넥션 추가.
        Connector connector = new Connector();
        connector.setPort(8080);
        tomcat.setConnector(connector);

        /*
         컨텍스트 생성 및 설정.
         컨텍스트는 톰캣에서 애플리케이션의 최상위 경로를 지정하며, 웹 애플리케이션의 루트를 정의하는 역할.
         */
        Context context = tomcat.addContext("", "/");

        // 서블릿 추가 및 URL 매핑 설정.
        tomcat.addServlet("", "helloServlet", new HelloServlet());
        context.addServletMappingDecoded("/hello-servlet", "helloServlet");

        // 톰캣 시작.
        tomcat.start();
    }
}
