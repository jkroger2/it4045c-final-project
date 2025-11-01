package app.campassist.enterprise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the CampAssist Enterprise Spring Boot application.
 *
 * <p>This class is annotated with {@link SpringBootApplication}, which enables
 * component scanning, auto-configuration, and property support for the application.</p>
 *
 * <p>Running this class will start the embedded web server and initialize
 * the Spring application context.</p>
 */
@SpringBootApplication
public class EnterpriseApplication {

    /**
     * The main method, which serves as the entry point of the application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(EnterpriseApplication.class, args);
    }
}
