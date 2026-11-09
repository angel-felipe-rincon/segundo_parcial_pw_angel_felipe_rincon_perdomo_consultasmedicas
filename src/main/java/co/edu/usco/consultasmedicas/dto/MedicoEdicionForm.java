package co.edu.usco.consultasmedicas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Datos editables de un medico")
public class MedicoEdicionForm {

    @Schema(description = "Nombre del medico (maximo 40 caracteres)", example = "Carlos Ramirez")
    @NotBlank(message = "El nombre del medico es obligatorio")
    @Size(max = 40, message = "El nombre del medico no puede superar los 40 caracteres")
    @Pattern(regexp = "^[A-Za-z0-9ÁÉÍÓÚÜÑáéíóúüñ. ]*$", message = "El nombre del medico solo puede tener letras, numeros, puntos y espacios")
    private String nombre;

    @Schema(description = "Especialidad (maximo 45 caracteres)", example = "Medicina General")
    @NotBlank(message = "La especialidad es obligatoria")
    @Size(max = 45, message = "La especialidad no puede superar los 45 caracteres")
    private String especialidad;
}
