package org.iesvdm.tutorial;

import jakarta.transaction.Transactional;
import org.iesvdm.tutorial.domain.Actor;
import org.iesvdm.tutorial.domain.Pelicula;
import org.iesvdm.tutorial.repository.ActorRepository;
import org.iesvdm.tutorial.repository.ActorRepository;
import org.iesvdm.tutorial.repository.PeliculaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashSet;

@SpringBootTest
public class PeliculaActorTest {
    @Autowired
    PeliculaRepository peliculaRepository;
    @Autowired
    ActorRepository actorRepository;

    @Test
    void crearPelicula(){
        Pelicula pelicula = new Pelicula(0, "Titulo1", null, null, null);
        peliculaRepository.save(pelicula);
    }
    @Test
    void crearActor(){
        Actor actor = new Actor(1, "nombre", "a1", "a2", new Date(), new HashSet<>());
        actorRepository.save(actor);
    }
    @Test
    void borrarPelicula(){
        Pelicula pelicula1 = peliculaRepository.findById(1L).orElse(null);
        peliculaRepository.delete(pelicula1);
    }
    @Test
    void borrarActor(){
        Actor actor = actorRepository.findById(1).orElse(null);
        actorRepository.delete(actor);
    }
    @Test
    void asociarPeliculaActor(){
        Pelicula pelicula = new Pelicula(0, "Titulo5", null, new HashSet<>(), null);
        Actor actor = new Actor(1, "nombre", "a1", "a2", new Date(), new HashSet<>());
        pelicula.getActores().add(actor);
        actor.getPeliculas().add(pelicula);
        peliculaRepository.save(pelicula);
        actorRepository.save(actor);
    }
    @Test
    @Transactional
    void desasociarPeliculaActor(){
        Pelicula pelicula1 = peliculaRepository.findById(1L).orElse(null);
        Actor actor = actorRepository.findById(2).orElse(null);
        pelicula1.getActores().remove(pelicula1);
        actor.getPeliculas().remove(actor);

        peliculaRepository.save(pelicula1);
        actorRepository.save(actor);
    }
}
