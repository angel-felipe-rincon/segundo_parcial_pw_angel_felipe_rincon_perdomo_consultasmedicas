package co.edu.usco.consultasmedicas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usco.consultasmedicas.dto.MedicoEdicionForm;
import co.edu.usco.consultasmedicas.dto.MedicoForm;
import co.edu.usco.consultasmedicas.exception.ConflictoException;
import co.edu.usco.consultasmedicas.exception.RecursoNoEncontradoException;
import co.edu.usco.consultasmedicas.model.Medico;
import co.edu.usco.consultasmedicas.model.Usuario;
import co.edu.usco.consultasmedicas.repository.ConsultaRepository;
import co.edu.usco.consultasmedicas.repository.MedicoRepository;
import co.edu.usco.consultasmedicas.repository.RoleRepository;
import co.edu.usco.consultasmedicas.repository.UsuarioRepository;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Medico> getAllMedicos() {
        return medicoRepository.findAll(Sort.by("id"));
    }

    public Medico getMedicoById(Long id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el medico con id " + id));
    }

    @Transactional
    public Medico createMedico(MedicoForm form) {
        String username = form.getUsername().trim();
        if (usuarioRepository.findByUsername(username) != null) {
            throw new ConflictoException("Ya existe un usuario con el nombre " + username);
        }

        Medico medico = new Medico(form.getNombre().trim(), form.getEspecialidad().trim());
        medico = medicoRepository.save(medico);

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(form.getPassword()));
        usuario.setEnabled(true);
        usuario.setNombre(medico.getNombre());
        usuario.setMedico(medico);
        usuario.getRoles().add(roleRepository.findByNombre("MEDICO"));
        usuarioRepository.save(usuario);

        return medico;
    }

    @Transactional
    public Medico updateMedico(Long id, MedicoEdicionForm form) {
        Medico medico = getMedicoById(id);
        medico.setNombre(form.getNombre().trim());
        medico.setEspecialidad(form.getEspecialidad().trim());
        return medicoRepository.save(medico);
    }

    @Transactional
    public void deleteMedico(Long id) {
        Medico medico = getMedicoById(id);
        // Si el médico tiene consultas asignadas no se elimina
        if (consultaRepository.existsByMedicoId(id)) {
            throw new ConflictoException("No se puede eliminar el medico porque tiene consultas asignadas");
        }
        usuarioRepository.deleteAll(usuarioRepository.findByMedicoId(id));
        usuarioRepository.flush();
        medicoRepository.delete(medico);
    }
}
