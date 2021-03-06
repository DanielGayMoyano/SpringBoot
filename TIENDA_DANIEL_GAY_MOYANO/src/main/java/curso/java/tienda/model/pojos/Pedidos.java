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
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "pedidos")
public class Pedidos {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@JoinColumn(name="id_usuario")
	@ManyToOne (cascade = CascadeType.MERGE)
	private Usuarios id_usuario;
	private Timestamp fecha;
	private String metodo_Pago;
	private String estado;
	private String num_Factura;
	private double total;

	public Pedidos(Timestamp fecha, String metodoPago, String estado, String num_Factura, double total) {
		super();
		this.fecha = fecha;
		this.metodo_Pago = metodoPago;
		this.estado = estado;
		this.num_Factura = num_Factura;
		this.total = total;
	}

}
