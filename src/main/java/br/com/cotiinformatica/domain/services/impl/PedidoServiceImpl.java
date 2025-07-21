package br.com.cotiinformatica.domain.services.impl;
import java.nio.channels.IllegalSelectorException;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cotiinformatica.domain.entities.Pedido;
import br.com.cotiinformatica.domain.events.PedidoCriadoEvent;
import br.com.cotiinformatica.domain.exceptions.PedidoNaoEncontradoException;
import br.com.cotiinformatica.domain.models.PedidoRequestModel;
import br.com.cotiinformatica.domain.models.PedidoResponseModel;
import br.com.cotiinformatica.domain.services.interfaces.PedidoService;
import br.com.cotiinformatica.infrastructure.outbox.OutboxMessage;
import br.com.cotiinformatica.infrastructure.repositories.OutboxMessageRepository;
import br.com.cotiinformatica.infrastructure.repositories.PedidoRepository;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
		
	private final PedidoRepository pedidoRepository;
	private final OutboxMessageRepository outboxMessageRepository;
	private final ModelMapper mapper;
	private final ObjectMapper objectMapper;
	
	@Transactional
	@Override
	public PedidoResponseModel criarPedido(PedidoRequestModel model) {
	
		//a partir da requisição, cria um pedido
		var pedido = mapper.map(model, Pedido.class);
				
		//salva o pedido no BD
		var pedidoCriado = pedidoRepository.save(pedido);
		
		//gera um evento para fila
		var event = new PedidoCriadoEvent(
				pedidoCriado.getId(),
				pedidoCriado.getDataPedido(),
				pedidoCriado.getValorPedido(),
				pedidoCriado.getNomeCliente(),
				pedidoCriado.getDescricaoPedido(),
				pedidoCriado.getStatus().toString()
				);
		
		//criar um registro para a tabela de saida (outbox)
		var message = new OutboxMessage();
		message.setAgregateType("Pedido"); //nome da entidade de dominio
		message.setAggregateId(pedidoCriado.getId().toString());
		message.setType("PedidoCriado");
		try {
			message.setPayload(objectMapper.writeValueAsString(event));
		} catch(JsonProcessingException e) {
			throw new IllegalStateException(e);
		}
		
		//Salvando o registro de saída no BD
		outboxMessageRepository.save(message);		
		
		return mapper.map(pedidoCriado, PedidoResponseModel.class);
	}
	@Override
	public PedidoResponseModel alterarPedido(UUID id, PedidoRequestModel model) {
		var pedido = pedidoRepository.findByIdAndAtivo(id)
						.orElseThrow(() -> new PedidoNaoEncontradoException(id));
		
		mapper.map(model, pedido);
		
		pedidoRepository.save(pedido);
		
		return mapper.map(pedido, PedidoResponseModel.class);
	}
	@Override
	public PedidoResponseModel inativarPedido(UUID id) {
		
		var pedido = pedidoRepository.findByIdAndAtivo(id)
				.orElseThrow(() -> new PedidoNaoEncontradoException(id));
		
		pedido.setAtivo(false);
		
		pedidoRepository.save(pedido);
		
		return mapper.map(pedido, PedidoResponseModel.class);
	}
	@Override
	public Page<PedidoResponseModel> consultarPedidos(Pageable pageable) {
		
		var pedidos = pedidoRepository.findAtivos(pageable);
		
		return pedidos.map(pedido -> mapper.map(pedido, PedidoResponseModel.class));
	}
	@Override
	public PedidoResponseModel obterPedidoPorId(UUID id) {

		var pedido = pedidoRepository.findByIdAndAtivo(id)
				.orElseThrow(() -> new PedidoNaoEncontradoException(id));
		
		return mapper.map(pedido, PedidoResponseModel.class);
	}
}



