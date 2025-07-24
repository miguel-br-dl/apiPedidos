package br.com.cotiinformatica.configurations;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
	
	@Value("${cors.allowed-origins}")
	private String[] allowedOrigins;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
	
		//Capturar os domínios que tem permissão para acessar a nossa API
		var origins = Arrays.asList(allowedOrigins);
		
		//definindo a política
		registry.addMapping("/**") //Política aplicada para todos os endpoints
			.allowedOrigins(origins.toArray(new String[0])) //domínios com permissão de acesso
			.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE") //permissões de métodos
			.allowedHeaders("*"); //aplicando as configurações
	}
}

