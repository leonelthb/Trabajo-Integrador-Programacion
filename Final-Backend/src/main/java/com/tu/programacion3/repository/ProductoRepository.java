package com.tu.programacion3.repository;

import com.tu.programacion3.entities.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

// tamboem repo especifico para prodicto
public class ProductoRepository extends BaseRepository<Producto>{
    // contructor
    public ProductoRepository() {
        super(Producto.class); // este repo trabaja con producto
    }
    // buscar productos de una categoria
    public List<Producto> buscarPorCategoria(Long categoriaId){ // consulta jpql
        // creamos el entity
        EntityManager em = emf.createEntityManager();

        try{
            //consulta jpql
            String jpql = " select p from Producto p " + " Where p.categoria.id = :categoriaId " +  "And p.eliminado = false";

            // cfrear consulta tipada
            TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);

            // asignar parametro
            query.setParameter("categoriaId", categoriaId);

            // ejecutar la consulta
            return query.getResultList();
        }finally{
            em.close(); // liberar el recurso
        }
    }

    public Producto buscarPorNombre(String nombre) {

        EntityManager em = emf.createEntityManager();

        try {

            String jpql = "SELECT p FROM Producto p WHERE p.nombre = :nombre AND p.eliminado = false";

            TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);

            query.setParameter("nombre", nombre);

            return query.getResultStream().findFirst().orElse(null);

        } finally {

            em.close();

        }
    }
}

