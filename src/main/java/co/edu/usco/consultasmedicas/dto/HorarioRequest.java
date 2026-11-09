package co.edu.usco.consultasmedicas.dto;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Nuevo horario de una consulta")
public class HorarioRequest {

    @Schema(description = "Hora de inicio (HH:mm)", type = "string", format = "time", example = "09:00")
    @NotNull(message = "La hora de inicio es obligatoria")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime horaInicio;

    @Schema(description = "Hora de finalizacion (HH:mm)", type = "string", format = "time", example = "09:30")
    @NotNull(message = "La hora de finalizacion es obligatoria")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime horaFin;

    @Schema(hidden = true)
    @AssertTrue(message = "La hora de finalizacion debe ser posterior a la hora de inicio")
    public boolean isHorarioValido() {
        return horaInicio == null || horaFin == null || horaFin.isAfter(horaInicio);
    }
}
