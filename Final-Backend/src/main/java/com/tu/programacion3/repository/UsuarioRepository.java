package com.tu.programacion3.repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import com.tu.programacion3.entities.Usuario;

public class UsuarioRepository extends BaseRepository<Usuario> {

    public UsuarioRepository() {
        super(Usuario.class);
    }

    // buscar un usuario por su mail
    public Usuario buscarPorMail(String mail) {

        EntityManager em = emf.createEntityManager();

        try {

            String jpql = "SELECT u FROM Usuario u WHERE u.mail = :mail";

            TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);

            query.setParameter("mail", mail);

            return query.getSingleResult();

        } catch (Exception e) {

            return null;

        } finally {

            em.close();

        }

    }

}