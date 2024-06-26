package hiroliang.tools.loggercontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Slf4j
@SpringBootApplication
@EnableAspectJAutoProxy
public class LoggerControllerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoggerControllerApplication.class, args);
        log.info("""
                
                
                -------------------------------------------------------------------------------
                
                Application Logger Test Start !
                
                -------------------------------------------------------------------------------
                
                """);
    }

}
