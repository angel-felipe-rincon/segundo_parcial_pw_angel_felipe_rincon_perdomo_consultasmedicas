package co.edu.usco.consultasmedicas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usco.consultasmedicas.dto.ConsultaForm;
import co.edu.usco.consultasmedicas.dto.HorarioRequest;
import co.edu.usco.consultasmedicas.exception.RecursoNoEncontradoException;
import co.edu.usco.consultasmedicas.model.Consulta;
import co.edu.usco.consultasmedicas.model.Medico;
import co.edu.usco.consultasmedicas.model.Usuario;
import co.edu.usco.consultasmedicas.repository.ConsultaRepository;
import co.edu.usco.consultasmedicas.repository.MedicoRepository;
import co.edu.usco.consultasmedicas.repository.UsuarioRepository;

@Service
public class ConsultaService {

    private static final String ADMINISTRADOR = "ADMINISTRADOR";
    private static final String MEDICO = "MEDICO";
    private static final String PACIENTE = "PACIENTE";

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Consulta> getConsultasDe(String username) {
        Usuario usuario = getUsuario(username);
        if (tieneRol(usuario, ADMINISTRADOR)) {
            return consultaRepository.findAll(Sort.by("id"));
        }
        if (tieneRol(usuario, MEDICO) && usuario.getMedico() != null) {
            return consultaRepository.findByMedicoIdOrderByIdAsc(usuario.getMedico().getId());
        }
        if (tieneRol(usuario, PACIENTE) && usuario.getNombre() != null) {
            return consultaRepository.findByNombrePacienteIgnoreCaseOrderByIdAsc(usuario.getNombre().trim());
        }
        return List.of();
    }

    public Consulta getConsultaById(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la consulta con id " + id));
    }

    public Consulta getConsultaDe(Long id, String username) {
        Consulta consulta = getConsultaById(id);
        Usuario usuario = getUsuario(username);
        if (!esAsignada(usuario, consulta)) {
            throw new AccessDeniedException("La consulta no esta asignada a este usuario");
        }
        return consulta;
    }

    public Consulta getConsultaParaHorario(Long id, String username) {
        Consulta consulta = getConsultaById(id);
        Usuario usuario = getUsuario(username);
        boolean permitido = tieneRol(usuario, ADMINISTRADOR)
                || (tieneRol(usuario, PACIENTE) && esAsignada(usuario, consulta));
        if (!permitido) {
            throw new AccessDeniedException("No puede modificar el horario de esta consulta");
        }
        return consulta;
    }

    public List<Medico> getAllMedicos() {
        return medicoRepository.findAll(Sort.by("id"));
    }

    @Transactional
    public Consulta createConsulta(ConsultaForm form) {
        Consulta consulta = new Consulta();
        copiar(form, consulta);
        return consultaRepository.save(consulta);
    }

    @Transactional
    public Consulta updateConsulta(Long id, ConsultaForm form) {
        Consulta consulta = getConsultaById(id);
        copiar(form, consulta);
        return consultaRepository.save(consulta);
    }

    @Transactional
    public Consulta updateHorario(Long id, HorarioRequest request, String username) {
        Consulta consulta = getConsultaParaHorario(id, username);
        consulta.setHoraInicio(request.getHoraInicio());
        consulta.setHoraFin(request.getHoraFin());
        return consultaRepository.save(consulta);
    }

    @Transactional
    public void deleteConsulta(Long id) {
        consultaRepository.delete(getConsultaById(id));
    }

    private void copiar(ConsultaForm form, Consulta consulta) {
        consulta.setNombrePaciente(form.getNombrePaciente().trim());
        consulta.setMotivo(form.getMotivo().trim());
        consulta.setNumeroConsultorio(form.getNumeroConsultorio());
        consulta.setHoraInicio(form.getHoraInicio());
        consulta.setHoraFin(form.getHoraFin());
        consulta.setMedico(medicoRepository.findById(form.getMedicoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el medico con id " + form.getMedicoId())));
    }

    private Usuario getUsuario(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            throw new AccessDeniedException("Usuario no valido");
        }
        return usuario;
    }

    private boolean tieneRol(Usuario usuario, String rol) {
        return usuario.getRoles().stream().anyMatch(r -> r.getNombre().equals(rol));
    }

    private boolean esAsignada(Usuario usuario, Consulta consulta) {
        if (tieneRol(usuario, ADMINISTRADOR)) {
            return true;
        }
        if (tieneRol(usuario, MEDICO)) {
            return usuario.getMedico() != null && usuario.getMedico().getId().equals(consulta.getMedico().getId());
        }
        if (tieneRol(usuario, PACIENTE)) {
            return usuario.getNombre() != null
                    && usuario.getNombre().trim().equalsIgnoreCase(consulta.getNombrePaciente().trim());
        }
        return false;
    }
}
