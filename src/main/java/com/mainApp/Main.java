package com.mainApp;

import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    //    public static void main(String[] args) throws Exception {
//        Tomcat tomcat = new Tomcat();
//        Secrets sec = new Secrets();
//        String port = sec.get(Secrets.keys.PORT.name(), "8080");
//
//        if (port != null) {
//            tomcat.setPort(Integer.parseInt(port));
//        }
//
//        tomcat.getConnector(); // triggers connector creation
//
//        Context context = tomcat.addContext(
//                "",
//                new File(".").getAbsolutePath()
//        );
//
//        Tomcat.addServlet(
//                context,
//                "dispatcher",
//                new DispatcherServlet(
//                        new AnnotationConfigWebApplicationContext() {{
//                            register(AppConfig.class);
//                            register(DBSpec.class);
//                            scan("com.mainApp");
//                        }}
//                )
//        ).setLoadOnStartup(1);
//
//        context.addServletMappingDecoded("/", "dispatcher");
//
//        tomcat.start();
//        tomcat.getServer().await();
//        System.out.println("Server started on port 8080");
//    }
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

    }

    public static void stopServer(Tomcat tomcat) throws Exception {
        if (tomcat != null) {
            tomcat.stop();
            tomcat.destroy();
            System.out.println("Server stopped.");
        }
    }
}
