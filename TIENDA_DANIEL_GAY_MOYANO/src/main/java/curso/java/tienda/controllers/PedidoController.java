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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import curso.java.tienda.model.pojos.Detalles_Pedido;
import curso.java.tienda.model.pojos.Pedidos;
import curso.java.tienda.model.pojos.Productos;
import curso.java.tienda.model.pojos.Usuarios;
import curso.java.tienda.services.ServicesDetallesPedidos;
import curso.java.tienda.services.ServicesPedidos;
import curso.java.tienda.services.ServicesProductos;
import curso.java.tienda.services.ServicesUsuarios;
import curso.java.tienda.utils.Routes;

@Controller
@RequestMapping(value = "", method = { RequestMethod.GET, RequestMethod.POST })
public class PedidoController {

	List<Productos> carrito = new ArrayList<Productos>();

	@Autowired
	ServicesDetallesPedidos servicesDetallesPedidos;

	@Autowired
	ServicesProductos servicesProductos;

	@Autowired
	ServicesPedidos servicesPedidos;

	@GetMapping("/listaPedidos")
	public String listaPedidos(Model model, HttpSession sesion) {

		Usuarios usuario = (Usuarios) sesion.getAttribute("usuario");
		if (usuario == null) {
			return Routes.LOGIN;
		} else {
			List<Pedidos> listaPedidos = servicesPedidos.recuperarPedidos(String.valueOf(usuario.getId()));
			model.addAttribute("listaPedidos", listaPedidos);
			return Routes.PEDIDO;
		}
	}

	@GetMapping("/realizarPedido")
	public String realizarPedido(HttpSession sesion) {
		Double totalCarrito=(Double) sesion.getAttribute("total");
		if (sesion.getAttribute("usuario") != null) {
			if (totalCarrito!=0) {
				double total = 0;
				List<Productos> almacen = servicesProductos.recuperarProductos();
				// System.out.println(almacen);
				List<Productos> carrito = (List<Productos>) sesion.getAttribute("carrito");
				// System.out.println(carrito);
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
				// System.out.println("precio total despues de impuestos:" + total);
				// System.out.println(carrito);

				sesion.setAttribute("total", total);
				sesion.setAttribute("carrito", carrito);
				return Routes.METODO_PAGO;
			} else {
				return Routes.REDIRECT_TO_CATALOGO;
			}
		} else {
			return Routes.LOGIN;
		}
	}

	@GetMapping("/crearPedido")
	public String crearPedido(@RequestParam String paymentMethod, Model model, HttpSession sesion) {
		Usuarios usuario = (Usuarios) sesion.getAttribute("usuario");
		if (usuario == null) {

		}
		String metodoPago = paymentMethod;
		double total = 0;
		try {
			total = (double) sesion.getAttribute("total");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println(total + " " + metodoPago);

		// PedidoDAO pedidoDao = new PedidoDAO();

		List<Pedidos> listaPedidos = servicesPedidos.recuperarPedidos();
		List<Productos> carrito = (List<Productos>) sesion.getAttribute("carrito");
		// System.out.println(carrito);
		Pedidos aux = listaPedidos.get(listaPedidos.size() - 1);
		Pedidos pedidoPojo = new Pedidos();

		pedidoPojo.setId_usuario(usuario);
		pedidoPojo.setFecha(new Timestamp(System.currentTimeMillis()));
		pedidoPojo.setMetodo_Pago(metodoPago);

		pedidoPojo.setEstado("PE");// poner con las iniciales
		pedidoPojo.setNum_Factura(usuario.getNombre().concat(String.valueOf(aux.getId())));
		pedidoPojo.setTotal(total);

		// System.out.println(pedidoPojo);
		sesion.setAttribute("pedido", pedidoPojo);
		model.addAttribute("listaPedidos");
		Pedidos pedido = servicesPedidos.guardarPedido(pedidoPojo);
		for (Productos producto : carrito) {
			Detalles_Pedido temp = new Detalles_Pedido();
			temp.setId_pedido(pedido);
			temp.setId_producto(producto);
			temp.setImpuesto(producto.getImpuesto());
			temp.setPrecio_unidad(Float.parseFloat(String.valueOf(producto.getPrecio())));
			temp.setTotal(Double.parseDouble(String.valueOf(sesion.getAttribute("total"))));
			temp.setUnidades(producto.getStock());
			Productos auxiliar = servicesProductos.recuperarProducto(producto.getId());
			auxiliar.setStock(auxiliar.getStock() - producto.getStock());
			servicesProductos.guardarProducto(auxiliar);
			servicesDetallesPedidos.insertarDetallesPedido(temp);
		}
		return Routes.PEDIDO;
	}

	@GetMapping("/verDetallesPedido")
	public String verDetallesPedido(@RequestParam("pedido") String id, Model model) {
		Pedidos pedido = servicesPedidos.recuperarPedido(Integer.valueOf(id));
		List<Detalles_Pedido> lista = servicesDetallesPedidos.recuperarDetallesPorPedido(Integer.valueOf(id));
		// System.out.println(lista);
		model.addAttribute("lista", lista);
		model.addAttribute("total", pedido.getTotal());
		model.addAttribute("id", id);
		return Routes.DETALLE_PEDIDO;
	}

	@GetMapping("/cancelarPedido")
	public String cancelarPedido(@RequestParam("id") String id) {
		Pedidos pedido = servicesPedidos.recuperarPedido(Integer.parseInt(id));
		pedido.setEstado("PC");
		servicesPedidos.guardarPedido(pedido);
		return Routes.PEDIDO;
	}
}
