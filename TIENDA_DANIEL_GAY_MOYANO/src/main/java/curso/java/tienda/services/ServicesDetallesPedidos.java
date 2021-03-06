package curso.java.tienda.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.pojos.Detalles_Pedido;
import curso.java.tienda.model.repository.DetallesPedidoRepository;

@Service
public class ServicesDetallesPedidos {

	@Autowired
	DetallesPedidoRepository detallesPedidoRepository;

	public void insertarDetallesPedido(Detalles_Pedido detallesPedido) {
		detallesPedidoRepository.save(detallesPedido);
	}


	public Detalles_Pedido recuperarDetallesPedido(int id) {
		return detallesPedidoRepository.getById(id);
	}

	public void borrarDetallesPedido(int id) {
		detallesPedidoRepository.deleteById(id);
	}

	public List<Detalles_Pedido> recuperarDetallesPedidos(String query) {
		return detallesPedidoRepository.findAll();
	}

	public List<Detalles_Pedido> recuperarDetallesPorPedido(int idPedido){
		List<Detalles_Pedido> lista=detallesPedidoRepository.findAll();
		List<Detalles_Pedido> aux=new ArrayList<Detalles_Pedido>();
		for(Detalles_Pedido detalle: lista) {
			System.out.println(idPedido+"  "+detalle.getId_pedido());
			if(idPedido==detalle.getId_pedido().getId()) {
				
				aux.add(detalle);
			}
		}
		return aux;
	}
}
