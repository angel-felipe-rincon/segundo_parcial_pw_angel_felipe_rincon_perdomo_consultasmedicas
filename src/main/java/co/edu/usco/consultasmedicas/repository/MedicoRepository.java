package co.edu.usco.consultasmedicas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.usco.consultasmedicas.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
}
