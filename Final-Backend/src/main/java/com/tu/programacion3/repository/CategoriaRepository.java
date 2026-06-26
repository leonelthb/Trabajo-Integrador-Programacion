package com.tu.programacion3.repository;

import com.tu.programacion3.entities.Categoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

// repositorio específico para Categoria
public class CategoriaRepository extends BaseRepository<Categoria> {

    public CategoriaRepository() {
        super(Categoria.class);
    }

    // buscar categoria por nombre
    public Categoria buscarPorNombre(String nombre) {

        EntityManager em = emf.createEntityManager();

        try {

            String jpql = "SELECT c FROM Categoria c WHERE c.nombre = :nombre AND c.eliminado = false";

            TypedQuery<Categoria> query = em.createQuery(jpql, Categoria.class);

            query.setParameter("nombre", nombre);

            return query.getResultStream().findFirst().orElse(null);

        } finally {
            em.close();
        }
    }
}