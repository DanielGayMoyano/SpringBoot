package curso.java.tienda.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.pojos.Roles;
import curso.java.tienda.model.repository.RolesRepository;

@Service
public class ServicesRoles {
	@Autowired
	RolesRepository rolesRepository;

	public Roles guardarRol(Roles rol) {
		return rolesRepository.save(rol);
	}
	
	public Roles recuperarRol(int id) {
		return rolesRepository.getById(id);
	}
}
