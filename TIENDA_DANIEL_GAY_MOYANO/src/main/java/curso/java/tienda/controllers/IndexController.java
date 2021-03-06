package curso.java.tienda.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import curso.java.tienda.model.pojos.Productos;
import curso.java.tienda.services.ServicesPedidos;
import curso.java.tienda.services.ServicesProductos;
import curso.java.tienda.services.ServicesUsuarios;
import curso.java.tienda.utils.Routes;

@Controller
@RequestMapping(value = "", method = { RequestMethod.GET, RequestMethod.POST })
public class IndexController {

	List<Productos> carrito = new ArrayList<Productos>();

	@GetMapping("")
	public String index(HttpSession sesion) {
		// sesion.setAttribute("carrito", new ArrayList<Productos>());

		sesion.setAttribute("carrito", carrito);
		// revisar y mover usuario a sesion
		// System.out.println(model.getAttribute("usuario"));
		return Routes.REDIRECT_TO_CATALOGO;
	}

}
