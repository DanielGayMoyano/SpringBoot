package curso.java.tienda.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.pojos.Pedidos;
import curso.java.tienda.model.repository.PedidosRepository;

@Service
public class ServicesPedidos {

	@Autowired
	PedidosRepository pedidosRepository;

	public Pedidos guardarPedido(Pedidos pedido) {
		return pedidosRepository.save(pedido);
	}

	public Pedidos recuperarPedido(int id) {
		return pedidosRepository.getById(id);
	}

	public void borrarPedido(int id) {
		pedidosRepository.deleteById(id);
	}

	public List<Pedidos> recuperarPedidos() {
		return pedidosRepository.findAll();
	}

	public List<Pedidos> recuperarPedidos(String id_usuario){
		return pedidosRepository.findByUsuarioId(id_usuario);
	}
}
