package org.iesvdm.tutorial.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.ToString.Exclude;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    private String nombre;

    private String apellido1;

    private String apellido2;

    private Date ultima_actualizacion;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Pelicula> peliculas;
}
