package curso.java.tienda.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import curso.java.tienda.model.pojos.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer>{

}
