package curso.java.tienda.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import curso.java.tienda.model.pojos.Productos;
import curso.java.tienda.services.ServicesPedidos;
import curso.java.tienda.services.ServicesProductos;
import curso.java.tienda.services.ServicesUsuarios;
import curso.java.tienda.utils.Routes;

@Controller
@RequestMapping(value = "", method = { RequestMethod.GET, RequestMethod.POST })
public class ProductosController {

	@Autowired
	ServicesProductos servicesProductos;

	@GetMapping("/catalogo")
	public String verCatalogo(Model model, @RequestParam(required = false) String select,
			@RequestParam(required = false) String paginaActual) {
		
		int prodPorPag;
		/*
		 * supuestamente era para paginar una consulta Page<Productos>
		 * page=sp.recuperarProductos(pageable); model.addAttribute("page",page); return
		 * "productos/catalogo-page";
		 */
		// sesion.setAttribute("select", select);
		select = null;
		// System.out.println(select);

		if (select == null) {
			prodPorPag = 5;
		} else {
			prodPorPag = Integer.valueOf(select);
		}
		int numProductosTotal = servicesProductos.recuperarNumeroItems();
		String numPag;
		if (paginaActual == null) {
			numPag = "1";
		} else {
			numPag = paginaActual;
		}

		int start = Integer.parseInt(numPag);

		if (start == 1) {
		} else {
			start = start - 1;
			start = start * prodPorPag + 1;
		}

		List<Productos> lista = servicesProductos.paginarProductos(start - 1, prodPorPag);

		model.addAttribute("lista", lista);
		model.addAttribute("start", start);
		model.addAttribute("prodPorPag", prodPorPag);
		// sesion.setAttribute("prodPorPag", prodPorPag);
		int numPagTotal;
		if (numProductosTotal % prodPorPag != 0) {
			numPagTotal = (numProductosTotal / prodPorPag) + 1;

		} else {
			numPagTotal = numProductosTotal / prodPorPag;
		}
		model.addAttribute("numPaginas", numPagTotal);

		model.addAttribute("paginaActual", numPag);
		if (Integer.valueOf(numPag) < numPagTotal) {
			model.addAttribute("siguiente", (Integer.valueOf(numPag) + 1));
		} else {
			model.addAttribute("siguiente", numPag);
		}
		if (Integer.valueOf(numPag) > 1) {
			model.addAttribute("anterior", (Integer.valueOf(numPag) - 1));
		} else {
			model.addAttribute("anterior", numPag);
		}

		return Routes.CATALOGO;
	}

	@GetMapping("/meterAlCarrito")
	public String addProducto(@RequestParam("producto") String id, HttpSession sesion) {
		// Productos producto =
		// servicesProductos.recuperarProducto(Integer.parseInt(id));
		// sesion.setAttribute("carrito", carrito);
		List<Productos> carrito = (List<Productos>) sesion.getAttribute("carrito");
		List<Productos> catalogo = servicesProductos.recuperarProductos();

		sesion.setAttribute("carrito", carrito);

		int ident = Integer.valueOf(id);
		boolean bandera = false;

		for (int i = 0; i < carrito.size(); i++) {
			if (carrito.get(i).getId() == ident) {
				for (Productos producto : catalogo) {
					if (producto.getId() == ident) {
						producto.setStock(producto.getStock() - 1);
					}
				}
				carrito.get(i).setStock(carrito.get(i).getStock() + 1);
				bandera = true;
			}
		}
		if (!bandera) {
			for (Productos producto : catalogo) {
				if (producto.getId() == ident) {
					Productos aux = new Productos(producto);
					aux.setStock(1);
					if (producto.getStock() == null) {
						producto.setStock(0);
					}
					producto.setStock(producto.getStock() - 1);
					carrito.add(aux);
				}
			}
		}
		// System.out.println(carrito);
		sesion.setAttribute("carrito", carrito);
		return Routes.REDIRECT_TO_CATALOGO;

	}

	@GetMapping("/eliminarDelCarrito")
	public String deleteProducto(@RequestParam("producto") String id, HttpSession sesion) {
		Productos producto = servicesProductos.recuperarProducto(Integer.parseInt(id));
		List<Productos> carrito = (List<Productos>) sesion.getAttribute("carrito");
		List<Productos> aux = new ArrayList<Productos>();
		for (Productos product : carrito) {
			if (product.getId() != producto.getId()) {
				aux.add(product);
			}
		}
		sesion.setAttribute("carrito", aux);
		return Routes.REDIRECT_TO_CARRITO;
	}

	@GetMapping("/carrito")
	public String carrito(HttpSession sesion, Model model) {

		double total = 0;
		// System.out.println("carrito= "+sesion.getAttribute("carrito"));
		List<Productos> almacen = servicesProductos.recuperarProductos();
		List<Productos> carrito = (List<Productos>) sesion.getAttribute("carrito");
		for (Productos producto : carrito) {
			// System.out.println(producto);
			for (Productos p : almacen) {
				if (producto.getId() == p.getId()) {
					if (producto.getStock() > p.getStock()) {
						producto.setStock(p.getStock());
					}
				}
			}
			total = total + (producto.getPrecio() * producto.getStock());
			// total+=(producto.getPrecio()*producto.getStock());
			// System.out.println(total);
		}
		sesion.setAttribute("total", total);
		return Routes.CARRITO;
	}

	@GetMapping("/verDetalles")
	public String verDetalles(@RequestParam("producto") String id, HttpSession sesion, Model model) {

		Productos producto = servicesProductos.recuperarProducto(Integer.valueOf(id));
		model.addAttribute("producto", producto);
		return Routes.DETALLE_PRODUCTO;
	}

}
