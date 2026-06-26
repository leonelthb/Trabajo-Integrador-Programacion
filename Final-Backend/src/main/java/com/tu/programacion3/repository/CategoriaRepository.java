package com.tu.programacion3.repository;
import com.tu.programacion3.entities.Categoria;


// repositorio especifico para categoria
public class CategoriaRepository extends BaseRepository<Categoria> {
    // contructor
    public CategoriaRepository() {
        super(Categoria.class); // enviar la clase categoria al repo base
    }

}
