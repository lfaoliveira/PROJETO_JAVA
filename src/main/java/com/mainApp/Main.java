package com.mainApp;
import com.mainApp.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // usa beans aqui
        context.close();
        String a = String.format("CONTEXT: %s", context);
        System.out.println(a);
        System.out.println("Aplicacao finalizada com sucesso!");
    }
}
