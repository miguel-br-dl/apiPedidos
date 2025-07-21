package br.com.cotiinformatica.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.domain.models.PedidoRequestModel;
import br.com.cotiinformatica.domain.models.PedidoResponseModel;
import br.com.cotiinformatica.domain.services.interfaces.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(
	name = "Controle de pedidos",
	description = "Serviços para gerenciamento de solicitações de pedidos."
)
@RequiredArgsConstructor
public class PedidosController {
	
	private final PedidoService pedidoService;
	
	@Operation(
			summary = "Cadastro de solicitações de pedido",
			description = "Cria uma nova solicitação de pedido no sistema."
	)
	@PostMapping
	public ResponseEntity<PedidoResponseModel> post(@RequestBody @Valid PedidoRequestModel model) {
		
		var response = pedidoService.criarPedido(model);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@Operation(
			summary = "Atualização de pedido",
			description = "Modifica uma solicitação de pedido no sistema."
	)
	@PutMapping("{id}")
	public ResponseEntity<PedidoResponseModel> put(@PathVariable UUID id, @RequestBody @Valid PedidoRequestModel model) {
		
		var response = pedidoService.alterarPedido(id, model);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@Operation(
			summary = "Inativação de pedido",
			description = "Inativa uma solicitação de pedido no sistema."
	)
	@DeleteMapping("{id}")
	public ResponseEntity<PedidoResponseModel> delete(@PathVariable UUID id) {
		var response = pedidoService.inativarPedido(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@Operation(
			summary = "Consulta de solicitações de pedido",
			description = "Retorna consultas paginadas de solicitações de pedido no sistema."
	)
	@GetMapping
	public ResponseEntity<Page<PedidoResponseModel>> get(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "25") int size
			) {

		var pageable = PageRequest.of(page, size);
		var response = pedidoService.consultarPedidos(pageable);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@Operation(
			summary = "Consulta de solicitação de pedido por ID",
			description = "Retorna um pedido no sistema através do ID informado."
	)
	@GetMapping("{id}")
	public ResponseEntity<PedidoResponseModel> getById(@PathVariable UUID id) {

		var response = pedidoService.obterPedidoPorId(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	

}
