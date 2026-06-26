package com.tu.programacion3.repository;

import com.tu.programacion3.entities.Pedido;
import com.tu.programacion3.enums.Estado;
import com.tu.programacion3.entities.Usuario;
import com.tu.programacion3.enums.FormaPago;
import com.tu.programacion3.temp.ItemPedidoTemp;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import com.tu.programacion3.entities.Producto;
import com.tu.programacion3.entities.DetallePedido;



// Repositorio específico para Pedido
public class PedidoRepository extends BaseRepository<Pedido> {

    // Constructor
    public PedidoRepository() {

        super(Pedido.class);

    }

    // Buscar pedidos por estado
    public List<Pedido> buscarPorEstado(Estado estado) {

        EntityManager em = emf.createEntityManager();

        try {

            String jpql = """
                SELECT p
                FROM Pedido p
                WHERE p.estado = :estado
                AND p.eliminado = false
                """;

            TypedQuery<Pedido> query = em.createQuery(jpql, Pedido.class);

            query.setParameter("estado", estado);

            return query.getResultList();

        } finally {

            em.close();

        }

    }
    // Crear un pedido completo dentro de una única transacción
    public Pedido crearPedido(
            Usuario usuario,
            FormaPago formaPago,
            List<ItemPedidoTemp> items) {


        // Crear el EntityManager
        EntityManager em = emf.createEntityManager();

        try {

            // Iniciar la transacción
            em.getTransaction().begin();

            // Recuperar el usuario dentro del mismo EntityManager
            usuario = em.find(Usuario.class, usuario.getId());

            // Crear el pedido
            Pedido pedido = Pedido.builder()
                    .usuario(usuario)
                    .fecha(java.time.LocalDate.now())
                    .estado(Estado.PENDIENTE)
                    .formapago(formaPago)
                    .build();


            // Recorrer todos los productos seleccionados
            for (ItemPedidoTemp item : items) {

                // Recuperar el producto dentro del mismo EntityManager
                Producto producto = em.find(Producto.class, item.getIdProducto());

                // Verificar que el producto exista
                if (producto == null) {

                    throw new RuntimeException("Producto inexistente.");

                }

                // Verificar stock
                if (producto.getStock() < item.getCantidad()) {

                    throw new RuntimeException(
                            "Stock insuficiente para el producto: "
                                    + producto.getNombre());

                }

                // Crear el detalle del pedido
                DetallePedido detalle = DetallePedido.builder()
                        .producto(producto)
                        .cantidad(item.getCantidad())
                        .precio(producto.getPrecio())
                        .subtotal(producto.getPrecio() * item.getCantidad())
                        .build();

                // Asociar el detalle al pedido
                pedido.addDetallePedido(detalle);

                // Descontar el stock
                producto.setStock(
                        producto.getStock() - item.getCantidad());

            }
            // Calcular el total del pedido
            pedido.calcularTotal();

            // Persistir el pedido
            em.persist(pedido);

            // Confirmar la transacción
            em.getTransaction().commit();

            // Devolver el pedido ya persistido
            return pedido;

        } catch (Exception e) {

            // Si ocurre un error, deshacer toda la transacción
            em.getTransaction().rollback();

            e.printStackTrace();

            return null;

        } finally {

            // Liberar el EntityManager
            em.close();

        }

    }

    // Buscar pedidos de un usuario
    public List<Pedido> buscarPorUsuario(Long idUsuario) {

        EntityManager em = emf.createEntityManager();

        try {

            String jpql = """
                SELECT p
                FROM Pedido p
                WHERE p.usuario.id = :idUsuario
                AND p.eliminado = false
                """;

            TypedQuery<Pedido> query = em.createQuery(jpql, Pedido.class);

            query.setParameter("idUsuario", idUsuario);

            return query.getResultList();

        } finally {

            em.close();

        }

    }


}

