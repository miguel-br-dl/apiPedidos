package br.com.cotiinformatica.domain.models;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class PedidoRequestModel {
	@NotNull(message = "Por favor, informe a data do pedido.")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataPedido;
	
	@NotNull(message = "Por favor, informe o valor do pedido.")
	@DecimalMin(value = "0.01", message = "O valor do pedido deve ser maior do que zero.")
	private Double valorPedido;
	
	@NotEmpty(message = "Por favor, informe o nome do cliente.")
	@Size(min = 8, max = 100, message = "O nome do cliente deve ter de 8 a 100 caracteres.")
	private String nomeCliente;
	
	@Size(max = 500, message = "A descrição do pedido deve ter no máximo 500 caracteres.")
	private String descricaoPedido;
	
	@NotEmpty(message = "Por favor, informe o status do pedido.")
	@Pattern(
		regexp = "^(RECEBIDO|PROCESSADO|ENVIADO|ENTREGUE|CANCELADO)$",
		message = "Status inválido. Valores aceitos: RECEBIDO, PROCESSADO, ENVIADO, ENTREGUE, CANCELADO."
	)
	private String status;
}


