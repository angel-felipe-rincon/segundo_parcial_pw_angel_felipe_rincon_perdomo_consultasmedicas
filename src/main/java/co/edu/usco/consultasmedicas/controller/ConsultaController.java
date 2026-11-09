package co.edu.usco.consultasmedicas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import co.edu.usco.consultasmedicas.dto.ConsultaForm;
import co.edu.usco.consultasmedicas.dto.HorarioRequest;
import co.edu.usco.consultasmedicas.model.Consulta;
import co.edu.usco.consultasmedicas.service.ConsultaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @GetMapping
    public String getAllConsultas(Model model, Authentication authentication) {
        model.addAttribute("consultas", consultaService.getConsultasDe(authentication.getName()));
        return "lista-consultas";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        model.addAttribute("consulta", new ConsultaForm());
        model.addAttribute("medicos", consultaService.getAllMedicos());
        return "nueva-consulta";
    }

    @PostMapping("/guardar")
    public String guardarConsulta(@Valid @ModelAttribute("consulta") ConsultaForm consulta,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("medicos", consultaService.getAllMedicos());
            return "nueva-consulta";
        }
        consultaService.createConsulta(consulta);
        return "redirect:/consultas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Consulta c = consultaService.getConsultaById(id);
        ConsultaForm form = new ConsultaForm();
        form.setNombrePaciente(c.getNombrePaciente());
        form.setMotivo(c.getMotivo());
        form.setNumeroConsultorio(c.getNumeroConsultorio());
        form.setHoraInicio(c.getHoraInicio());
        form.setHoraFin(c.getHoraFin());
        form.setMedicoId(c.getMedico().getId());
        model.addAttribute("consulta", form);
        model.addAttribute("consultaId", id);
        model.addAttribute("medicos", consultaService.getAllMedicos());
        return "editar-consulta";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarConsulta(@PathVariable Long id,
            @Valid @ModelAttribute("consulta") ConsultaForm consulta,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("consultaId", id);
            model.addAttribute("medicos", consultaService.getAllMedicos());
            return "editar-consulta";
        }
        consultaService.updateConsulta(id, consulta);
        return "redirect:/consultas";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarConsulta(@PathVariable Long id) {
        consultaService.deleteConsulta(id);
        return "redirect:/consultas";
    }

    @GetMapping("/horario/{id}")
    public String mostrarFormularioHorario(@PathVariable Long id, Model model, Authentication authentication) {
        Consulta c = consultaService.getConsultaParaHorario(id, authentication.getName());
        HorarioRequest cambio = new HorarioRequest();
        cambio.setHoraInicio(c.getHoraInicio());
        cambio.setHoraFin(c.getHoraFin());
        model.addAttribute("consulta", c);
        model.addAttribute("cambio", cambio);
        return "horario-consulta";
    }

    @PostMapping("/horario/{id}")
    public String actualizarHorario(@PathVariable Long id,
            @Valid @ModelAttribute("cambio") HorarioRequest cambio,
            BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            model.addAttribute("consulta", consultaService.getConsultaParaHorario(id, authentication.getName()));
            return "horario-consulta";
        }
        consultaService.updateHorario(id, cambio, authentication.getName());
        return "redirect:/consultas";
    }
}
