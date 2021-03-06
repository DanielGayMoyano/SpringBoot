package curso.java.tienda.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import curso.java.tienda.model.pojos.Usuarios;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Integer> {
	
	@Query(value = "select * from Usuarios where email=?1 and clave=?2", nativeQuery = true)
	List<Usuarios> buscarUsuarioLogin(String email, String clave);
}
