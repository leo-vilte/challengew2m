package com.challenge.w2m.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info =@Info(
                title = "Superhero API",
                version = "${api.version}",
                contact = @Contact(
                        name = "Leo Vilte", email = "leo_vilte@hotmail.com"
                )
        )
)
public class OpenAPISecurityConfiguration {

}
