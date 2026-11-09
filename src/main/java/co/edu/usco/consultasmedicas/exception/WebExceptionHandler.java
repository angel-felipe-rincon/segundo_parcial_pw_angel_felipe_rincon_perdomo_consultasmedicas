package co.edu.usco.consultasmedicas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import co.edu.usco.consultasmedicas.controller.ConsultaController;
import co.edu.usco.consultasmedicas.controller.MedicoController;

@ControllerAdvice(assignableTypes = { ConsultaController.class, MedicoController.class })
public class WebExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String noEncontrado(RecursoNoEncontradoException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        return "404";
    }
}
