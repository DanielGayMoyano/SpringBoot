package curso.java.tienda.model.pojos;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="productos")
public class Productos {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@JoinColumn(name="id_categoria")
	@ManyToOne (cascade = CascadeType.MERGE)
	private Categorias id_categoria;
	private String nombre;
	private String descripcion;
	private Double precio;
	private Integer stock;
	private Timestamp fecha_alta;
	private Timestamp fecha_baja;
	private Float impuesto;
	private String imagen;

	public Productos(Productos producto) {
		super();
		this.id = producto.id;
		this.id_categoria = producto.id_categoria;
		this.nombre = producto.nombre;
		this.descripcion = producto.descripcion;
		this.precio = producto.precio;
		this.stock = producto.stock;
		this.impuesto = producto.impuesto;
		this.imagen = producto.imagen;
	}

	@Override
	public String toString() {
		return "Productos [id=" + id + ", id_categoria=" + id_categoria + ", nombre=" + nombre + ", descripcion="
				+ descripcion + ", precio=" + precio + ", stock=" + stock + ", impuesto=" + impuesto + ", imagen="
				+ imagen + "]";
	}

}
