package com.tu.programacion3.repository;
import com.tu.programacion3.entities.Base;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import java.util.List;
import jakarta.persistence.TypedQuery;
import com.tu.programacion3.utils.JPAUtil;



public class BaseRepository<T extends Base> {

    protected EntityManagerFactory emf;
    protected Class<T> entityClass; // guarda una entidad en la base de datos

    public BaseRepository(Class<T> entityClass) {

        // Obtener la única instancia del EntityManagerFactory
        this.emf = JPAUtil.getEntityManagerFactory();

        // Guardar la clase de la entidad
        this.entityClass = entityClass;
    }

    public void guardar(T entidad){ // guardar una entidad en la base de datos
        // crear un entity manager para interactuar con JPA
        EntityManager em = emf.createEntityManager();

        try {

            //iniciar transaccion
            em.getTransaction().begin();

            //persistir la entidad recibida
            em.persist(entidad);

            //confirmar cambios en la base
            em.getTransaction().commit();

        }catch(Exception e) {
            // si algo sale mal hacemos rollback
            em.getTransaction().rollback();

            //motrar el error
            e.printStackTrace();
        }finally {
            em.close(); // liberar el recurso
        }

    }

    public Optional<T> buscarPorId(Long id){
        // crear el entitymanager
        EntityManager em = emf.createEntityManager();

        try {

            //buscar la entidad utilizando la clase recibida
            return Optional.ofNullable(em.find(entityClass, id));
        }finally {
            em.close(); // liberar el recurso
        }
    }
    // listar todas las entidades activas no elimindas
    public List<T> listarActivos(){
        // crear el entitymanager
        EntityManager em = emf.createEntityManager();

        try{ // consulta jpql generica para categoria, producto,usario, etc
            String jpql = "Select e from " + entityClass.getSimpleName() + " e where e.eliminado = false";

            // crear la consulta tipada
            TypedQuery<T> query = em.createQuery(jpql, entityClass);

            // ejecutar y retornar los resultados
            return query.getResultList();

        }finally {
            em.close(); // liberar los recursos
        }


    }
    // el borrado logico
    public boolean eliminarLogico(Long id) {
        // creamos el entitymanager
        EntityManager em = emf.createEntityManager();
        try {
            // iniciar la transaccion
            em.getTransaction().begin();

            //buscar la entidad por la id
            T entidad = em.find(entityClass, id);

            // si no exite devuelve false
            if (entidad == null) {

                em.getTransaction().rollback();
                return false;
            }

            // marcar como eliminado
            entidad.setEliminado(true);

            //actualizar
            em.merge(entidad);

            //commit
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            //roll back en caso de error
            em.getTransaction().rollback();

            e.printStackTrace(); // mostrar el error
            return false;
        } finally {
            // liberar el recurso
            em.close();
        }
    }

    // actualizar una entidad existente
    public void actualizar(T entidad){
        // el entity manager
        EntityManager em = emf.createEntityManager();

        try{
            // inicio de la transaccion
            em.getTransaction().begin();

            //merge que actualiza la entidad en la base
            em.merge(entidad);

            //commit
            em.getTransaction().commit();
        }catch(Exception e) {
            //roll back por si sale todo mal
            em.getTransaction().rollback();

            e.printStackTrace(); // motrar el error en caso que lo hubiera
        }finally {
            em.close(); // liberar recursos
        }
    }
}


// aca permitimos guardar cualquier entidad que herede desde base
// try catch por si algo falla