package co.edu.usco.consultasmedicas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Datos para crear un medico y su cuenta de acceso")
public class MedicoForm {

    @Schema(description = "Nombre del medico (maximo 40 caracteres)", example = "Carlos Ramirez")
    @NotBlank(message = "El nombre del medico es obligatorio")
    @Size(max = 40, message = "El nombre del medico no puede superar los 40 caracteres")
    @Pattern(regexp = "^[A-Za-z0-9ÁÉÍÓÚÜÑáéíóúüñ. ]*$", message = "El nombre del medico solo puede tener letras, numeros, puntos y espacios")
    private String nombre;

    @Schema(description = "Especialidad (maximo 45 caracteres)", example = "Medicina General")
    @NotBlank(message = "La especialidad es obligatoria")
    @Size(max = 45, message = "La especialidad no puede superar los 45 caracteres")
    private String especialidad;

    @Schema(description = "Usuario con el que el medico inicia sesion", example = "medico3")
    @NotBlank(message = "El usuario es obligatorio")
    @Size(min = 3, max = 45, message = "El usuario debe tener entre 3 y 45 caracteres")
    @Pattern(regexp = "^[A-Za-z0-9_.]*$", message = "El usuario solo puede tener letras, numeros, punto y guion bajo")
    private String username;

    @Schema(description = "Clave del medico (minimo 6 caracteres)", example = "medico123")
    @NotBlank(message = "La clave es obligatoria")
    @Size(min = 6, max = 60, message = "La clave debe tener entre 6 y 60 caracteres")
    private String password;
}
