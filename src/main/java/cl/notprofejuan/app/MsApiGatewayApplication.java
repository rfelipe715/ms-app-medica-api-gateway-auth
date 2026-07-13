package cl.notprofejuan.app;

import cl.notprofejuan.app.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(JwtProperties.class)
@SpringBootApplication
public class MsApiGatewayApplication {

	public static void main(String[] args) {
        SpringApplication.run(MsApiGatewayApplication.class, args);
	}

}
