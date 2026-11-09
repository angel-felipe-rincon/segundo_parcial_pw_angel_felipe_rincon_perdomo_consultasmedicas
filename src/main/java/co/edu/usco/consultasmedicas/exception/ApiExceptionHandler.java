package co.edu.usco.consultasmedicas.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import co.edu.usco.consultasmedicas.controller.ConsultaRestController;
import co.edu.usco.consultasmedicas.controller.MedicoRestController;

@RestControllerAdvice(assignableTypes = { ConsultaRestController.class, MedicoRestController.class })
public class ApiExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> noEncontrado(RecursoNoEncontradoException ex) {
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cuerpo);
    }

    @ExceptionHandler(ConflictoException.class)
    public ResponseEntity<Map<String, Object>> conflicto(ConflictoException ex) {
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(cuerpo);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> datosInvalidos(MethodArgumentNotValidException ex) {
        Map<String, String> detalles = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> detalles.put(e.getField(), e.getDefaultMessage()));
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("error", "Datos invalidos");
        cuerpo.put("detalles", detalles);
        return ResponseEntity.badRequest().body(cuerpo);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> cuerpoIlegible(HttpMessageNotReadableException ex) {
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("error", "El cuerpo de la peticion no es un JSON valido");
        return ResponseEntity.badRequest().body(cuerpo);
    }
}
