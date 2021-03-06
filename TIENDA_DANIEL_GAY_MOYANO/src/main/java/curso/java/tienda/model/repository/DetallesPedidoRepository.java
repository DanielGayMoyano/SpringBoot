package curso.java.tienda.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import curso.java.tienda.model.pojos.Detalles_Pedido;

@Repository
public interface DetallesPedidoRepository extends JpaRepository<Detalles_Pedido, Integer> {

}
