package com.egg.biblioteca.repositorios;

import com.egg.biblioteca.entidades.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String>{
    
    @Query("SELECT a FROM Autor a ORDER BY a.nombre")
    public List<Autor> buscarAutor();
    
}   
