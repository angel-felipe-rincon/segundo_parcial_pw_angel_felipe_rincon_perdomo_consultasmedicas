package co.edu.usco.consultasmedicas.model;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "consultas")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_paciente", nullable = false, length = 40)
    private String nombrePaciente;

    @Column(nullable = false, length = 100)
    private String motivo;

    @Column(name = "numero_consultorio", nullable = false)
    private Integer numeroConsultorio;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;
}
