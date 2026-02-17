package com.mainApp;

import com.mainApp.config.AppConfig;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.mainApp.config.Secrets;

import java.io.File;


public class Main {

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();

        String port = Secrets.get("PORT");
        if (port != null) {
            tomcat.setPort(Integer.parseInt(port));
        }

        tomcat.getConnector(); // triggers connector creation

        Context context = tomcat.addContext(
                "",
                new File(".").getAbsolutePath()
        );

        Tomcat.addServlet(
                context,
                "dispatcher",
                new DispatcherServlet(
                        new AnnotationConfigWebApplicationContext() {{
                            register(AppConfig.class);
                        }}
                )
        ).setLoadOnStartup(1);

        context.addServletMappingDecoded("/", "dispatcher");

        tomcat.start();
        tomcat.getServer().await();
        System.out.println("Server started on port 8080");
    }

    public static void stopServer(Tomcat tomcat) throws Exception {
        if (tomcat != null) {
            tomcat.stop();
            tomcat.destroy();
            System.out.println("Server stopped.");
        }
    }
}
