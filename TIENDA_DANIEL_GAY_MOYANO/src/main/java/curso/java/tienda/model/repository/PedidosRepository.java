package curso.java.tienda.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import curso.java.tienda.model.pojos.Pedidos;

@Repository
public interface PedidosRepository extends JpaRepository<Pedidos,Integer>{
	@Query(value = "select * from Pedidos where id_usuario=?1 ", nativeQuery = true)
	List<Pedidos> findByUsuarioId(String id_usuario);
}
