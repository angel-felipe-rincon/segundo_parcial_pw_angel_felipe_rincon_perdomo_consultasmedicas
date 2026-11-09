package co.edu.usco.consultasmedicas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.usco.consultasmedicas.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByNombre(String nombre);
}
