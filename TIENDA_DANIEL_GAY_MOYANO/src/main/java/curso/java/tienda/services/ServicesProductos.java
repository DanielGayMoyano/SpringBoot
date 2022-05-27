package curso.java.tienda.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.pojos.Productos;
import curso.java.tienda.model.repository.ProductosRepository;


@Service
public class ServicesProductos {

	//Connection conexion;

	@Autowired
	ProductosRepository productosRepository;

	public Productos guardarProducto(Productos producto) {
		return productosRepository.save(producto);
	}

	public Productos recuperarProducto(int id) {
		return productosRepository.getById(id);
	}

	public void borrarProducto(int id) {
		productosRepository.deleteById(id);
	}

	public int recuperarNumeroItems() {
		return recuperarProductos().size();
	}

	public List<Productos> recuperarProductos() {
		return productosRepository.findAll();
	}

	public List<Productos> paginarProductos(int start, int total) {

//		ArrayList<Productos> aux = new ArrayList<Productos>();
//
//		if (conexion != null) {
//			try {
//				PreparedStatement preSta = conexion
//						.prepareStatement("select p from Productos p limit " + (start - 1) + "," + total);
//				ResultSet rs = preSta.executeQuery();
//				while (rs.next()) {
//					Productos producto = new Productos();
//					producto.setId(rs.getInt("id"));
//					producto.setNombre(rs.getString("nombre"));
//					producto.setPrecio(rs.getFloat("precio"));
//					producto.setStock(rs.getInt("stock"));
//					producto.setDescripcion(rs.getString("descripcion"));
//					producto.setImagen(rs.getString("imagen"));
//					producto.setImpuesto(rs.getFloat("impuesto"));
//					aux.add(producto);
//				}
//
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		} else {
//			conexion = Conexion.getConexion();
//		}
//		return aux;
//
		return productosRepository.paginarProductos(start, total);
	}

	public Page<Productos> recuperarProductos(Pageable pageable) {
		// TODO Auto-generated method stub
		return productosRepository.findAll(pageable);
	}
}