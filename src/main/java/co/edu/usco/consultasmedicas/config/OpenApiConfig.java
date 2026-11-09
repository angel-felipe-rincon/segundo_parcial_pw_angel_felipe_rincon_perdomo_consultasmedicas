package co.edu.usco.consultasmedicas.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "API de Consultas Medicas",
        version = "1.0",
        description = "Servicios web para gestionar medicos y consultas medicas. "
                + "Roles: ADMINISTRADOR (todo), MEDICO (solo consulta sus consultas) y "
                + "PACIENTE (consulta y actualiza el horario de sus propias consultas). "
                + "Para probar: inicie sesion en /login con el mismo navegador, o use el boton Authorize (usuario y clave)."))
@SecurityScheme(name = "basicAuth", type = SecuritySchemeType.HTTP, scheme = "basic")
public class OpenApiConfig {
}
