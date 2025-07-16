package br.com.cotiinformatica.domain.services.interfaces;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import br.com.cotiinformatica.domain.models.PedidoRequestModel;
import br.com.cotiinformatica.domain.models.PedidoResponseModel;
public interface PedidoService {
	PedidoResponseModel criarPedido(PedidoRequestModel model);
	
	PedidoResponseModel alterarPedido(UUID id, PedidoRequestModel model);
	
	PedidoResponseModel inativarPedido(UUID id);
	
	Page<PedidoResponseModel> consultarPedidos(Pageable pageable);
	
	PedidoResponseModel obterPedidoPorId(UUID id);
}


