package curso.java.tienda.model.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import curso.java.tienda.model.pojos.Productos;


@Repository
public interface ProductosRepository extends JpaRepository<Productos, Integer>{
	
	@Modifying
	@Transactional
	@Query(value = "select * from Productos LIMIT :total   OFFSET :start ", nativeQuery = true)
	List<Productos> paginarProductos(Integer start, Integer total);
	
}
