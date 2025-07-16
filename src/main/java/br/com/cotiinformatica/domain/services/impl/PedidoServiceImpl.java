package br.com.cotiinformatica.domain.services.impl;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.cotiinformatica.domain.entities.Pedido;
import br.com.cotiinformatica.domain.exceptions.PedidoNaoEncontradoException;
import br.com.cotiinformatica.domain.models.PedidoRequestModel;
import br.com.cotiinformatica.domain.models.PedidoResponseModel;
import br.com.cotiinformatica.domain.services.interfaces.PedidoService;
import br.com.cotiinformatica.infrastructure.repositories.PedidoRepository;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
		
	private final PedidoRepository pedidoRepository;
	private final ModelMapper mapper;
	
	@Override
	public PedidoResponseModel criarPedido(PedidoRequestModel model) {
	
		var pedido = mapper.map(model, Pedido.class);
				
		pedidoRepository.save(pedido);
		
		return mapper.map(pedido, PedidoResponseModel.class);
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



