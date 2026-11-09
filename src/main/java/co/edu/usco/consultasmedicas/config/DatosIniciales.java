package co.edu.usco.consultasmedicas.config;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import co.edu.usco.consultasmedicas.model.Consulta;
import co.edu.usco.consultasmedicas.model.Medico;
import co.edu.usco.consultasmedicas.model.Role;
import co.edu.usco.consultasmedicas.model.Usuario;
import co.edu.usco.consultasmedicas.repository.ConsultaRepository;
import co.edu.usco.consultasmedicas.repository.MedicoRepository;
import co.edu.usco.consultasmedicas.repository.RoleRepository;
import co.edu.usco.consultasmedicas.repository.UsuarioRepository;

@Component
public class DatosIniciales implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        crearRolSiNoExiste("ADMINISTRADOR");
        crearRolSiNoExiste("MEDICO");
        crearRolSiNoExiste("PACIENTE");

        if (usuarioRepository.count() > 0) {
            return;
        }

        Medico carlos = medicoRepository.save(new Medico("Carlos Ramirez", "Medicina General"));
        Medico ana = medicoRepository.save(new Medico("Ana Torres", "Pediatria"));

        crearUsuario("admin", "admin123", "Administrador", null, "ADMINISTRADOR");
        crearUsuario("medico1", "medico123", carlos.getNombre(), carlos, "MEDICO");
        crearUsuario("medico2", "medico123", ana.getNombre(), ana, "MEDICO");
        crearUsuario("paciente1", "paciente123", "Laura Gomez", null, "PACIENTE");
        crearUsuario("paciente2", "paciente123", "Pedro Martinez", null, "PACIENTE");

        crearConsulta("Laura Gomez", "Control general", 101, "08:00", "08:30", carlos);
        crearConsulta("Pedro Martinez", "Dolor de espalda", 102, "09:00", "09:30", carlos);
        crearConsulta("Laura Gomez", "Revision de alergia", 201, "10:00", "10:30", ana);
    }

    private void crearRolSiNoExiste(String nombre) {
        if (roleRepository.findByNombre(nombre) == null) {
            roleRepository.save(new Role(nombre));
        }
    }

    private void crearUsuario(String username, String clave, String nombre, Medico medico, String rol) {
        Usuario u = new Usuario();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(clave));
        u.setEnabled(true);
        u.setNombre(nombre);
        u.setMedico(medico);
        u.getRoles().add(roleRepository.findByNombre(rol));
        usuarioRepository.save(u);
    }

    private void crearConsulta(String paciente, String motivo, int consultorio, String inicio, String fin, Medico medico) {
        Consulta c = new Consulta();
        c.setNombrePaciente(paciente);
        c.setMotivo(motivo);
        c.setNumeroConsultorio(consultorio);
        c.setHoraInicio(LocalTime.parse(inicio));
        c.setHoraFin(LocalTime.parse(fin));
        c.setMedico(medico);
        consultaRepository.save(c);
    }
}
