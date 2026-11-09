package co.edu.usco.consultasmedicas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "medicos")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String nombre;

    @Column(nullable = false, length = 45)
    private String especialidad;

    public Medico() {
    }

    public Medico(String nombre, String especialidad) {
        this.nombre = nombre;
        this.especialidad = especialidad;
    }
}
