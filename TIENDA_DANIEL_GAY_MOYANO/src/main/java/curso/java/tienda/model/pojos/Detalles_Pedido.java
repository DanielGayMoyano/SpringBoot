package curso.java.tienda.model.pojos;

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
@Table(name = "detalles_pedido")
public class Detalles_Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@JoinColumn(name="id_pedido")
	@ManyToOne (cascade = CascadeType.MERGE)
	private Pedidos id_pedido;
	@JoinColumn(name="id_producto")
	@ManyToOne (cascade = CascadeType.MERGE)
	private Productos id_producto;
	private float precio_unidad;
	private int unidades;
	private float impuesto;
	private double total;

	public Detalles_Pedido(float precioUnidad, int unidades, float impuesto, double total) {
		super();
		this.precio_unidad = precioUnidad;
		this.unidades = unidades;
		this.impuesto = impuesto;
		this.total = total;
	}

}
