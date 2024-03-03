package org.iesvdm.tutorial;

import jakarta.transaction.Transactional;
import org.iesvdm.tutorial.domain.Categoria;
import org.iesvdm.tutorial.domain.Idioma;
import org.iesvdm.tutorial.domain.Pelicula;
import org.iesvdm.tutorial.repository.CategoriaRepository;
import org.iesvdm.tutorial.repository.PeliculaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashSet;

@SpringBootTest
public class PeliculaCategoriaTest {
    @Autowired
    PeliculaRepository peliculaRepository;
    @Autowired
    CategoriaRepository categoriaRepository;

    @Test
    void crearPelicula(){
        Pelicula pelicula = new Pelicula(0, "Titulo1", null, null, null);
        peliculaRepository.save(pelicula);
    }
    @Test
    void crearCategoria(){
        Categoria categoria = new Categoria(0, "categoria1", new Date(), new HashSet<>());
        categoriaRepository.save(categoria);
    }
    @Test
    void borrarPelicula(){
        Pelicula pelicula1 = peliculaRepository.findById(1L).orElse(null);
        peliculaRepository.delete(pelicula1);
    }
    @Test
    void borrarCategoria(){
        Categoria categoria = categoriaRepository.findById(1).orElse(null);
        categoriaRepository.delete(categoria);
    }
    @Test
    void asociarPeliculaCategoria(){
        Pelicula pelicula = new Pelicula(0, "Titulo5", null, null, new HashSet<>());
        Categoria categoria = new Categoria(0, "categoria1", new Date(), new HashSet<>());
        pelicula.getCategorias().add(categoria);
        categoria.getPeliculas().add(pelicula);
        peliculaRepository.save(pelicula);
        categoriaRepository.save(categoria);
    }
    @Test
    //@Transactional
    void desasociarPeliculaCategoria(){
        Pelicula pelicula1 = peliculaRepository.findById(1L).orElse(null);
        Categoria categoria = categoriaRepository.findById(1).orElse(null);
        pelicula1.getCategorias().remove(pelicula1);
        categoria.getPeliculas().remove(categoria);

        peliculaRepository.save(pelicula1);
        categoriaRepository.save(categoria);
    }
}
