package br.com.cotiinformatica.domain.entities;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import br.com.cotiinformatica.domain.enums.StatusPedido;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Table(name = "pedidos")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private UUID id;
	@Temporal(TemporalType.DATE)
	@Column(name = "data_pedido", nullable = false)
	private LocalDate dataPedido;
	@Column(name = "valor_pedido", nullable = false, precision = 15, scale = 2)
	private BigDecimal valorPedido;
	@Column(name = "nome_cliente", nullable = false, length = 100)
	private String nomeCliente;
	@Column(name = "descricao_pedido", length = 500)
	private String descricaoPedido;
	@Column(name = "ativo", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
	private Boolean ativo = true;
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private StatusPedido status;
}


