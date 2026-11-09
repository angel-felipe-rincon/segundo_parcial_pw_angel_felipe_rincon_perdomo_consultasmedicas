package co.edu.usco.consultasmedicas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.edu.usco.consultasmedicas.dto.MedicoEdicionForm;
import co.edu.usco.consultasmedicas.dto.MedicoForm;
import co.edu.usco.consultasmedicas.exception.ConflictoException;
import co.edu.usco.consultasmedicas.model.Medico;
import co.edu.usco.consultasmedicas.service.MedicoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    public String getAllMedicos(Model model) {
        model.addAttribute("medicos", medicoService.getAllMedicos());
        return "lista-medicos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("medico", new MedicoForm());
        return "nuevo-medico";
    }

    @PostMapping("/guardar")
    public String guardarMedico(@Valid @ModelAttribute("medico") MedicoForm medico, BindingResult result) {
        if (result.hasErrors()) {
            return "nuevo-medico";
        }
        try {
            medicoService.createMedico(medico);
        } catch (ConflictoException e) {
            result.rejectValue("username", "duplicado", e.getMessage());
            return "nuevo-medico";
        }
        return "redirect:/medicos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Medico m = medicoService.getMedicoById(id);
        MedicoEdicionForm form = new MedicoEdicionForm();
        form.setNombre(m.getNombre());
        form.setEspecialidad(m.getEspecialidad());
        model.addAttribute("medico", form);
        model.addAttribute("medicoId", id);
        return "editar-medico";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarMedico(@PathVariable Long id,
            @Valid @ModelAttribute("medico") MedicoEdicionForm medico,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("medicoId", id);
            return "editar-medico";
        }
        medicoService.updateMedico(id, medico);
        return "redirect:/medicos";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarMedico(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            medicoService.deleteMedico(id);
        } catch (ConflictoException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/medicos";
    }
}
