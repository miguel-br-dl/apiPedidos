package br.com.cotiinformatica.domain.exceptions;

import java.util.UUID;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PedidoNaoEncontradoException extends RuntimeException {
 
	private static final long serialVersionUID = -7315852459674962975L;

	private final UUID pedidoId;
	
	@Override
	public String getMessage() {
		return String.format("O pedido '%s' não foi encontrado", pedidoId);
	}
	
}
