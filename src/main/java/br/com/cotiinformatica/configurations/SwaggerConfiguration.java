package br.com.cotiinformatica.configurations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
@Configuration
public class SwaggerConfiguration {
	@Bean
	OpenAPI customOpenApi() {
		final String securitySchemeName = "bearerAuth";
		return new OpenAPI()
			.components(new Components()
				.addSecuritySchemes(securitySchemeName,
					new SecurityScheme()
						.name(securitySchemeName)
						.type(SecurityScheme.Type.HTTP)
						.scheme("bearer")
						.bearerFormat("JWT")))
			.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
			.info(new Info()
				.title("API de Pedidos - Treinamento TJ/PR Turma 02")
				.description("Projeto desenvolvido durante o curso **Java Avançado - Formação Arquiteto** da COTI Informática.")
				.version("v1.0.0")
				.termsOfService("https://www.cotiinformatica.com.br/termos")
				.contact(new Contact()
					.name("COTI Informática")
					.email("contato@cotiinformatica.com.br")
					.url("https://www.cotiinformatica.com.br"))
				.license(new License()
					.name("Licença MIT")
					.url("https://opensource.org/licenses/MIT")));
	}
}


