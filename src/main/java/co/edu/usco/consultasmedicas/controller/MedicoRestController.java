package co.edu.usco.consultasmedicas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.consultasmedicas.dto.MedicoEdicionForm;
import co.edu.usco.consultasmedicas.dto.MedicoForm;
import co.edu.usco.consultasmedicas.model.Medico;
import co.edu.usco.consultasmedicas.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/medicos")
@Tag(name = "Medicos", description = "Gestion de medicos (tabla independiente que alimenta la lista desplegable)")
@SecurityRequirement(name = "basicAuth")
public class MedicoRestController {

    @Autowired
    private MedicoService medicoService;

    @Operation(summary = "Listar los medicos", description = "Rol permitido: ADMINISTRADOR.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK: lista de medicos"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: no ha iniciado sesion"),
            @ApiResponse(responseCode = "403", description = "Forbidden: su rol no tiene permiso") })
    @GetMapping
    public List<Medico> listar() {
        return medicoService.getAllMedicos();
    }

    @Operation(summary = "Consultar un medico por id", description = "Rol permitido: ADMINISTRADOR.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK: medico encontrado"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: no ha iniciado sesion"),
            @ApiResponse(responseCode = "403", description = "Forbidden: su rol no tiene permiso"),
            @ApiResponse(responseCode = "404", description = "El medico no existe") })
    @GetMapping("/{id}")
    public Medico obtener(@Parameter(description = "Id del medico", example = "1") @PathVariable Long id) {
        return medicoService.getMedicoById(id);
    }

    @Operation(summary = "Crear un medico y su cuenta de acceso", description = "Rol permitido: ADMINISTRADOR.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created: medico creado"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: no ha iniciado sesion"),
            @ApiResponse(responseCode = "403", description = "Forbidden: su rol no tiene permiso"),
            @ApiResponse(responseCode = "409", description = "El usuario ya existe") })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Medico crear(@Valid @RequestBody MedicoForm form) {
        return medicoService.createMedico(form);
    }

    @Operation(summary = "Editar un medico", description = "Rol permitido: ADMINISTRADOR.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK: medico actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: no ha iniciado sesion"),
            @ApiResponse(responseCode = "403", description = "Forbidden: su rol no tiene permiso"),
            @ApiResponse(responseCode = "404", description = "El medico no existe") })
    @PutMapping("/{id}")
    public Medico actualizar(@Parameter(description = "Id del medico", example = "1") @PathVariable Long id,
            @Valid @RequestBody MedicoEdicionForm form) {
        return medicoService.updateMedico(id, form);
    }

    @Operation(summary = "Eliminar un medico",
            description = "Rol permitido: ADMINISTRADOR. No se puede eliminar un medico con consultas asignadas.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content: medico eliminado"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: no ha iniciado sesion"),
            @ApiResponse(responseCode = "403", description = "Forbidden: su rol no tiene permiso"),
            @ApiResponse(responseCode = "404", description = "El medico no existe"),
            @ApiResponse(responseCode = "409", description = "El medico tiene consultas asignadas") })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@Parameter(description = "Id del medico", example = "1") @PathVariable Long id) {
        medicoService.deleteMedico(id);
    }
}
