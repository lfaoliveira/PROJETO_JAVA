package com.mainApp;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.mainApp.config.AppConfig;


public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        // usa beans aqui
        context.close();
    }
}
