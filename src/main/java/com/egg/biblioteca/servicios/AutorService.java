package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    @Autowired
    AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) throws MiException {

        validar(nombre);

        Autor autor = new Autor();

        autor.setNombre(nombre);

        autorRepositorio.save(autor);

    }

    public List<Autor> listarAutores() {

        List<Autor> autores = new ArrayList();

//        autores = autorRepositorio.findAll();
        autores = autorRepositorio.buscarAutor();

        return autores;

    }

    public void modificarAutor(String nombre, String id) throws MiException {

        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Autor autor = respuesta.get();

            autor.setNombre(nombre);

            autorRepositorio.save(autor);

        }

    }

    public Autor getOne(String id) {

        return autorRepositorio.getOne(id);
    }

    public void validar(String nombre) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacio");
        }

    }

    @Transactional
    public void eliminar(String id) throws MiException {

        if (id == null) {
            throw new MiException("el id no puede ser nulo");
        }

        autorRepositorio.deleteById(id);;
    }

}
