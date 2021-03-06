package curso.java.tienda.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import curso.java.tienda.model.pojos.Categorias;
import curso.java.tienda.model.pojos.Productos;
import curso.java.tienda.model.pojos.Usuarios;
import curso.java.tienda.model.repository.CategoriasRepository;
import curso.java.tienda.services.ServicesDetallesPedidos;
import curso.java.tienda.services.ServicesPedidos;
import curso.java.tienda.services.ServicesProductos;
import curso.java.tienda.utils.Routes;

@Controller
@RequestMapping(value = "/admin", method = { RequestMethod.GET, RequestMethod.POST })
public class AdminController {

	List<Productos> carrito = new ArrayList<Productos>();

	
	@Autowired
	ServicesProductos servicesProductos;

	
	@Autowired
	CategoriasRepository categoriasRepository;

	@GetMapping("/nuevoProducto")
	public String nuevoProducto(Model model, HttpSession sesion) {
		Usuarios usuario = (Usuarios) sesion.getAttribute("usuario");
		if (usuario == null) {
			return Routes.LOGIN;
		}
		if (usuario.getId_rol().getId() == 3) {
			Productos producto = new Productos();
			model.addAttribute("nuevoProducto", producto);
			List<Categorias> lista = categoriasRepository.findAll();
			model.addAttribute("categorias", lista);
			return Routes.NUEVO_PRODUCTO;
		} else {
			model.addAttribute("errorCoincidencia", "Se necesitan permisos de administrador");
			return Routes.LOGIN;
		}
	}

	@GetMapping("/registrarProducto")
	public String registrarProducto(@ModelAttribute Productos producto, @RequestParam("categoria") String id) {
		Categorias categoria = categoriasRepository.getById(Integer.valueOf(id));
		producto.setId_categoria(categoria);
		producto.setFecha_alta(new Timestamp(System.currentTimeMillis()));
		Productos prod = servicesProductos.guardarProducto(producto);
		return Routes.REDIRECT_TO_REGISTRAR_PRODUCTO;
	}
}
