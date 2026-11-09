package co.edu.usco.consultasmedicas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.usco.consultasmedicas.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);

    List<Usuario> findByMedicoId(Long medicoId);
}
