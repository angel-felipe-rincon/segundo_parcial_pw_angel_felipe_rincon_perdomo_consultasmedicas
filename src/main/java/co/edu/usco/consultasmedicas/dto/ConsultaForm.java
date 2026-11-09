package co.edu.usco.consultasmedicas.dto;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Datos de una consulta medica")
public class ConsultaForm {

    @Schema(description = "Nombre del paciente (alfanumerico, maximo 40 caracteres)", example = "Laura Gomez")
    @NotBlank(message = "El nombre del paciente es obligatorio")
    @Size(max = 40, message = "El nombre del paciente no puede superar los 40 caracteres")
    @Pattern(regexp = "^[A-Za-z0-9ÁÉÍÓÚÜÑáéíóúüñ ]*$", message = "El nombre del paciente solo puede tener letras, numeros y espacios")
    private String nombrePaciente;

    @Schema(description = "Motivo de la consulta (maximo 100 caracteres)", example = "Control general")
    @NotBlank(message = "El motivo de la consulta es obligatorio")
    @Size(max = 100, message = "El motivo de la consulta no puede superar los 100 caracteres")
    private String motivo;

    @Schema(description = "Numero del consultorio", example = "101")
    @NotNull(message = "El numero de consultorio es obligatorio")
    @Min(value = 1, message = "El numero de consultorio debe ser mayor que 0")
    @Max(value = 9999, message = "El numero de consultorio no puede superar 9999")
    private Integer numeroConsultorio;

    @Schema(description = "Hora de inicio (HH:mm)", type = "string", format = "time", example = "08:00")
    @NotNull(message = "La hora de inicio es obligatoria")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime horaInicio;

    @Schema(description = "Hora de finalizacion (HH:mm)", type = "string", format = "time", example = "08:30")
    @NotNull(message = "La hora de finalizacion es obligatoria")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime horaFin;

    @Schema(description = "Id del medico (ver GET /api/medicos)", example = "1")
    @NotNull(message = "Debe seleccionar el medico")
    private Long medicoId;

    @Schema(hidden = true)
    @AssertTrue(message = "La hora de finalizacion debe ser posterior a la hora de inicio")
    public boolean isHorarioValido() {
        return horaInicio == null || horaFin == null || horaFin.isAfter(horaInicio);
    }
}
