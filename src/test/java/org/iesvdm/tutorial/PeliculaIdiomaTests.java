package org.iesvdm.tutorial;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.iesvdm.tutorial.domain.Actor;
import org.iesvdm.tutorial.domain.Categoria;
import org.iesvdm.tutorial.domain.Idioma;
import org.iesvdm.tutorial.domain.Pelicula;
import org.iesvdm.tutorial.repository.CategoriaRepository;
import org.iesvdm.tutorial.repository.IdiomaRepository;
import org.iesvdm.tutorial.repository.PeliculaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;
import java.util.HashSet;

/**
 * TEST ONETOMANY EAGER
 */
@SpringBootTest
public class PeliculaIdiomaTests {

    @Autowired
    IdiomaRepository idiomaRepository;
    @Autowired
    PeliculaRepository peliculaRepository;
    @Autowired
    CategoriaRepository categoriaRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;
    private TransactionTemplate transactionTemplate;
    @BeforeEach
    public void setUp() {
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Test
    void crearPelicula(){
        Idioma idioma = new Idioma(0, "español", null, new HashSet<>());
        idiomaRepository.save(idioma);
        Pelicula pelicula = new Pelicula(0, "Titulo1", null, null, null);
        pelicula.setIdioma(idioma);
        peliculaRepository.save(pelicula);
    }
    @Test
    void crearIdioma(){
        Idioma idioma = new Idioma(0,"idioma1", new Date(), new HashSet<>());
        idiomaRepository.save(idioma);
    }
    @Test
    void crearCategoria(){
        Categoria categoria = new Categoria(0, "categoria1", new Date(), new HashSet<>());
        categoriaRepository.save(categoria);
    }
    @Test
    void crearActor(){
        Actor actor = new Actor(0, "Actor1", "apellido1", "Apellido2", new Date(), new HashSet<>());

    }
    @Test
    void borrarPelicula(){
        Pelicula pelicula1 = peliculaRepository.findById(1L).orElse(null);
        peliculaRepository.delete(pelicula1);
    }
    @Test
    void asociarPeliculaConIdioma(){
        Pelicula pelicula = new Pelicula(0, "Titulo1", null, null, null);
        Idioma idioma = new Idioma(0,"alemán", new Date(2023-10-10), new HashSet<>());
        idioma.getPeliculas().add(pelicula);
        pelicula.setIdioma(idioma);
        idiomaRepository.save(idioma);
        peliculaRepository.save(pelicula);
    }

    @Test
    void desasociarPeliculaConIdioma(){
        Pelicula pelicula = peliculaRepository.findById(10L).orElse(null);
        Idioma idioma = idiomaRepository.findById(7L).orElse(null);
        idioma.setPeliculas(null);
        pelicula.setIdioma(null);
        idiomaRepository.save(idioma);
        peliculaRepository.save(pelicula);

    }

    @Test
    @Order(1)
    void grabarMultiplesPeliculasIdioma() {

        Idioma idioma1 = new Idioma(0, "español", null, new HashSet<>());
        idiomaRepository.save(idioma1);

        Pelicula pelicula1 = new Pelicula(0, "Pelicula1", idioma1, new HashSet<>(), new HashSet<>());
        idioma1.getPeliculas().add(pelicula1);
        peliculaRepository.save(pelicula1);

        Pelicula pelicula2 = new Pelicula(0, "Pelicula2", idioma1, new HashSet<>(), new HashSet<>());
        idioma1.getPeliculas().add(pelicula2);
        peliculaRepository.save(pelicula2);

        Idioma idioma2 = new Idioma(0, "inglés", null, new HashSet<>());
        idiomaRepository.save(idioma2);

        Pelicula pelicula3 = new Pelicula(0, "Pelicula3", idioma2, new HashSet<>(), new HashSet<>());
        idioma2.getPeliculas().add(pelicula3);
        peliculaRepository.save(pelicula3);

    }

    @Test
    @Order(2)
    void actualizarIdiomaPeliculaNull() {

        Pelicula pelicula1 = peliculaRepository.findById(1L).orElse(null);
        pelicula1.setIdioma(null);
        peliculaRepository.save(pelicula1);

    }

    @Test
    @Order(3)
    void actualizarIdiomaPeliculaAOtroIdioma() {

        Idioma idioma2 = idiomaRepository.findById(2L).orElse(null);
        Pelicula pelicula2 = peliculaRepository.findById(2L).orElse(null);
        pelicula2.setIdioma(idioma2);
        idioma2.getPeliculas().add(pelicula2);

        peliculaRepository.save(pelicula2);

    }

    @Test
    @Order(4)
    void eliminarPelicula() {
        Pelicula pelicula1 = peliculaRepository.findById(1L).orElse(null);
        peliculaRepository.delete(pelicula1);
    }

    @Test
    @Order(5)
    void eliminarPeliculasAsociadasAIdioma() {
        
        Idioma idioma2 = idiomaRepository.findById(2L).orElse(null);

        idioma2.getPeliculas().forEach(pelicula -> {//pelicula.setIdioma(null);
                                                    peliculaRepository.delete(pelicula);
        });
        //idiomaRepository.delete(idioma2);


    }
    @Test
    @Order(6)
    void eliminarPeliculasAsociadasAIdiomaEIdioma() {


        Idioma idioma1 = idiomaRepository.findById(3L).orElse(null);

            idioma1.getPeliculas().forEach(pelicula -> {//pelicula.setIdioma(null);
                peliculaRepository.delete(pelicula);
            });

            //ESTE 2o FIND HAY QUE HACERLO
        idioma1 = idiomaRepository.findById(1L).orElse(null);
        idiomaRepository.delete(idioma1);

    }
}
