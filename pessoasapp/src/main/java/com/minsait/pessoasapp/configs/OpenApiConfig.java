package com.minsait.pessoasapp.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("basicScheme",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
                .info(new Info()
                        .title("Projeto Final Spring Boot - Minsait")
                        .description("Este aplicativo tem o objetivo de fazer controle de cadastro de pessoas, bem como seus contatos")
                        .contact(new Contact().name("Alan Barbosa").email("alanbarbosadev@gmail.com").url("https://github.com/alanbarbosadev/Projeto-Final-Spring-JovensProfissionais-Minsait"))
                        .version("Versão 0.0.1-SNAPSHOT"));
    }
}
