package co.edu.usco.consultasmedicas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.usco.consultasmedicas.model.Consulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    List<Consulta> findByMedicoIdOrderByIdAsc(Long medicoId);

    List<Consulta> findByNombrePacienteIgnoreCaseOrderByIdAsc(String nombrePaciente);

    boolean existsByMedicoId(Long medicoId);
}
