package br.com.cotiinformatica.domain.events;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PedidoCriadoEvent(
		UUID id,				//Id do pedido
		LocalDate dataPedido,	//Data do pedido
		BigDecimal valorPedido,	//Valor do pedido
		String nomeCliente,		//Nome do cliente
		String descricaoPedido,	//Descrição do pedido
		String status			//Status do pedido
) {

}



