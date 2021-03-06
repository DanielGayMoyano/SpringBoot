package curso.java.tienda.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import curso.java.tienda.model.pojos.Productos;
import curso.java.tienda.model.pojos.Usuarios;
import curso.java.tienda.services.ServicesPedidos;
import curso.java.tienda.services.ServicesProductos;
import curso.java.tienda.services.ServicesRoles;
import curso.java.tienda.services.ServicesUsuarios;
import curso.java.tienda.utils.Routes;

@Controller
@RequestMapping(value = "", method = { RequestMethod.GET, RequestMethod.POST })
public class UsuarioController {

	@Autowired
	ServicesUsuarios servicesUsuarios;

	@Autowired
	ServicesRoles servicesRoles;

	@GetMapping("/entrar")
	public String entrarLogin(Model model, HttpSession sesion) {

		Usuarios usuario = (Usuarios) sesion.getAttribute("usuario");
		if (usuario == null) {
			model.addAttribute("usuario", new Usuarios());

			return Routes.LOGIN;
		} else {
			sesion.setAttribute("usuario", null);
			sesion.setAttribute("carrito", null);
			return Routes.INDEX;
		}
	}

	@PostMapping("/login")
	public String login(@ModelAttribute Usuarios usuario, HttpSession sesion, RedirectAttributes model) {
		// ServicesUsuarios su = new ServicesUsuarios();
		// no instanciar los servicios, sino crear el objeto y anotarlo el autowired
		Usuarios user=null;
		if (servicesUsuarios.recuperarUsuario(usuario.getEmail(), usuario.getClave()) != null) {

			user = servicesUsuarios.recuperarUsuario(usuario.getEmail(), usuario.getClave());
			// System.out.println(user);
			sesion.setAttribute("usuario", user);

		} else {
			// return entrarLogin();
			model.addFlashAttribute("errorCoincidencia", "El usuario o la contrase??a no son correctos");
			return "redirect:/entrar";
		}
		if(user.getId_rol().getId()==3) {
			return Routes.LISTA_PRODUCTOS;
		}
		return Routes.REDIRECT_TO_INDEX;
	}

	@GetMapping("/nuevoUsuario")
	public String nuevoUsuario(Model model) {
		Usuarios usuario = new Usuarios();

		model.addAttribute("usuarioRegistrar", usuario);
		return Routes.NUEVO_USUARIO;
	}

	@PostMapping("/registrar")
	public String registrar(@ModelAttribute Usuarios usuario, @RequestParam("clave") String clave,
			@RequestParam("repetirClave") String repetirClave, HttpSession sesion) {
		Usuarios user = usuario;
		if (!clave.equals(repetirClave)) {
			return Routes.NUEVO_USUARIO;
		}
		if (servicesUsuarios.recuperarUsuario(user.getEmail(), user.getClave()) != null) {

			return Routes.NUEVO_USUARIO;
		} else {

			user.setId_rol(servicesRoles.recuperarRol(1));
			servicesUsuarios.guardarUsuario(user);
			sesion.setAttribute("usuario", user);
			return Routes.INDEX;
		}

	}

	@GetMapping("/perfil")
	public String perfil(HttpSession sesion) {
		if (sesion.getAttribute("usuario") != null) {
			return Routes.PERFIL;
		} else {
			return Routes.LOGIN;
		}

	}

	@GetMapping("/cerrarSesion")
	public String cerrarSesion(HttpSession sesion) {
		List<Productos> carrito = (List<Productos>) sesion.getAttribute("carrito");
		carrito.clear();
		sesion.setAttribute("usuario", null);
		sesion.setAttribute("carrito", carrito);
		return Routes.INDEX;
	}

	@PostMapping("/editarUsuario")
	public String editarUsuario(HttpSession sesion, Model model) {
		Usuarios usuario = (Usuarios) sesion.getAttribute("usuario");
		// System.out.println(usuario);
		return Routes.EDITAR_USUARIO;
	}

	@PostMapping("/confirmarEdicionUsuario")
	public String confirmarEdicionUsuario(HttpSession sesion, @ModelAttribute Usuarios user) {

		Usuarios usuario = (Usuarios) sesion.getAttribute("usuario");
		user.setId_rol(usuario.getId_rol());
		user.setId(usuario.getId());
		user.setClave(usuario.getClave());
		sesion.setAttribute("usuario", user);
		servicesUsuarios.guardarUsuario(user);
		return Routes.PERFIL;
	}

}
