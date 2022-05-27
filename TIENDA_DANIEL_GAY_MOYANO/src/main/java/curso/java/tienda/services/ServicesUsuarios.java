package curso.java.tienda.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.pojos.Usuarios;
import curso.java.tienda.model.repository.UsuariosRepository;

@Service
public class ServicesUsuarios {
	@Autowired
	UsuariosRepository usuariosRepository;

	public Usuarios guardarUsuario(Usuarios usuario) {
		return usuariosRepository.save(usuario);
	}

	public Usuarios recuperarUsuario(String email, String clave) {
		if (email != null && clave != null) {
			if (!email.equalsIgnoreCase("") && !clave.equalsIgnoreCase("")) {
				List<Usuarios> lista=recuperarUsuarios();
				
				
				for(Usuarios usuario: lista) {
					if(usuario.getEmail().equals(email)&&usuario.getClave().equals(clave)) {
						return usuario;
					}
				}
			}
		}
		return null;
	}

	public Usuarios recuperarUsuario(int id) {
		Usuarios usuario=usuariosRepository.findById(id).get();
		return usuario;
	}

	public void borrarUsuario(int id) {
		usuariosRepository.deleteById(id);
	}

	public List<Usuarios> recuperarUsuarios() {
		List<Usuarios> lista=usuariosRepository.findAll();
		System.out.println(lista);
		return lista;
	}
}
