package co.edu.usco.consultasmedicas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.consultasmedicas.dto.ConsultaForm;
import co.edu.usco.consultasmedicas.dto.HorarioRequest;
import co.edu.usco.consultasmedicas.model.Consulta;
import co.edu.usco.consultasmedicas.service.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/consultas")
@Tag(name = "Consultas medicas", description = "Gestion de las consultas medicas")
@SecurityRequirement(name = "basicAuth")
public class ConsultaRestController {

    @Autowired
    private ConsultaService consultaService;

    @Operation(summary = "Listar las consultas visibles para el usuario",
            description = "ADMINISTRADOR ve todas, MEDICO solo las asignadas a el y PACIENTE solo las suyas. "
                    + "Roles permitidos: ADMINISTRADOR, MEDICO y PACIENTE.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK: lista de consultas"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: no ha iniciado sesion"),
            @ApiResponse(responseCode = "403", description = "Forbidden: su rol no tiene permiso") })
    @GetMapping
    public List<Consulta> listar(Authentication authentication) {
        return consultaService.getConsultasDe(authentication.getName());
    }

    @Operation(summary = "Consultar una consulta por id",
            description = "Roles permitidos: ADMINISTRADOR, MEDICO y PACIENTE, siempre que la consulta les pertenezca.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK: consulta encontrada"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: no ha iniciado sesion"),
            @ApiResponse(responseCode = "403", description = "Forbidden: la consulta no le pertenece"),
            @ApiResponse(responseCode = "404", description = "La consulta no existe") })
    @GetMapping("/{id}")
    public Consulta obtener(@Parameter(description = "Id de la consulta", example = "1") @PathVariable Long id,
            Authentication authentication) {
        return consultaService.getConsultaDe(id, authentication.getName());
    }

    @Operation(summary = "Registrar una consulta",
            description = "Rol permitido: ADMINISTRADOR.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created: consulta registrada"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: no ha iniciado sesion"),
            @ApiResponse(responseCode = "403", description = "Forbidden: su rol no tiene permiso"),
            @ApiResponse(responseCode = "404", description = "El medico no existe") })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Consulta crear(@Valid @RequestBody ConsultaForm form) {
        return consultaService.createConsulta(form);
    }

    @Operation(summary = "Modificar una consulta completa",
            description = "Rol permitido: ADMINISTRADOR.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK: consulta actualizada"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: no ha iniciado sesion"),
            @ApiResponse(responseCode = "403", description = "Forbidden: su rol no tiene permiso"),
            @ApiResponse(responseCode = "404", description = "La consulta o el medico no existe") })
    @PutMapping("/{id}")
    public Consulta actualizar(@Parameter(description = "Id de la consulta", example = "1") @PathVariable Long id,
            @Valid @RequestBody ConsultaForm form) {
        return consultaService.updateConsulta(id, form);
    }

    @Operation(summary = "Actualizar solo el horario de una consulta",
            description = "Roles permitidos: ADMINISTRADOR y PACIENTE (solo sobre sus propias consultas).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK: horario actualizado"),
            @ApiResponse(responseCode = "400", description = "Horario invalido"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: no ha iniciado sesion"),
            @ApiResponse(responseCode = "403", description = "Forbidden: su rol no tiene permiso o la consulta no le pertenece"),
            @ApiResponse(responseCode = "404", description = "La consulta no existe") })
    @PatchMapping("/{id}/horario")
    public Consulta actualizarHorario(@Parameter(description = "Id de la consulta", example = "1") @PathVariable Long id,
            @Valid @RequestBody HorarioRequest request, Authentication authentication) {
        return consultaService.updateHorario(id, request, authentication.getName());
    }

    @Operation(summary = "Eliminar una consulta", description = "Rol permitido: ADMINISTRADOR.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content: consulta eliminada"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: no ha iniciado sesion"),
            @ApiResponse(responseCode = "403", description = "Forbidden: su rol no tiene permiso"),
            @ApiResponse(responseCode = "404", description = "La consulta no existe") })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@Parameter(description = "Id de la consulta", example = "1") @PathVariable Long id) {
        consultaService.deleteConsulta(id);
    }
}
