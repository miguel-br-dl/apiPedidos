package br.com.cotiinformatica.controllers;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import br.com.cotiinformatica.domain.enums.StatusPedido;
import br.com.cotiinformatica.domain.models.PedidoRequestModel;
import br.com.cotiinformatica.domain.models.PedidoResponseModel;
import br.com.cotiinformatica.domain.services.interfaces.PedidoService;
@WebMvcTest(PedidosController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PedidosControllerTest {
	@Autowired
	private MockMvc mockMvc; //objeto para realizar as chamadas da API
	
	@Autowired
	private ObjectMapper objectMapper; //serializar e deserializar dados
	
	@SuppressWarnings("removal")
	@MockBean
	private PedidoService pedidoService; //interface da camada de serviço
	
	private Faker faker; //biblioteca para geração de dados	
	private String endpoint; //endereço dos serviços
	
	@BeforeEach
	public void setUp() {
		faker = new Faker();
		endpoint = "/api/v1/pedidos";
	}
	@Test
	@Order(1)
	@DisplayName("Deve executar POST /api/v1/pedidos com sucesso (201).")
	public void testPostPedido_Sucesso() throws Exception {
		
		//Dados de entrada e saída
		var request = gerarPedidoRequest();
		var response = gerarPedidoResponse(UUID.randomUUID(), request);
		
		//Configurando o comportamento da camada de serviço
		when(pedidoService.criarPedido(any(PedidoRequestModel.class))).thenReturn(response);
		
		//Fazendo a requisição para a API
		mockMvc.perform(post(endpoint)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(response.getId().toString()))
				.andExpect(jsonPath("$.nomeCliente").value(response.getNomeCliente()))
				.andExpect(jsonPath("$.dataPedido").value(response.getDataPedido().toString()))
				.andExpect(jsonPath("$.valorPedido").value(response.getValorPedido().toString()))
				.andExpect(jsonPath("$.descricaoPedido").value(response.getDescricaoPedido()))
				.andExpect(jsonPath("$.status").value(response.getStatus()));
	}
	
	@Test
	@Order(2)
	@DisplayName("Deve executar POST /api/v1/pedidos com dados inválidos (400).")
	public void testPostPedido_DadosInvalidos() throws Exception {
		
		//Dados de entrada e saída
		var request = new PedidoRequestModel();
		
		//Fazendo a requisição para a API
		mockMvc.perform(post(endpoint)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest());
	}
	@Test
	@Order(3)
	@DisplayName("Deve executar PUT /api/v1/pedidos com sucesso (200).")
	public void testPutPedido_Sucesso() throws Exception {
		//Gerando um ID para o pedido
		var id = UUID.randomUUID();
		
		//Dados de entrada e saída
		var request = gerarPedidoRequest();
		var response = gerarPedidoResponse(id, request);
				
		//Configurando o comportamento da camada de serviço
		when(pedidoService.alterarPedido(eq(id), any(PedidoRequestModel.class))).thenReturn(response);
				
		//Fazendo a requisição para a API
		mockMvc.perform(put(endpoint + "/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(response.getId().toString()))
				.andExpect(jsonPath("$.nomeCliente").value(response.getNomeCliente()))
				.andExpect(jsonPath("$.dataPedido").value(response.getDataPedido().toString()))
				.andExpect(jsonPath("$.valorPedido").value(response.getValorPedido().toString()))
				.andExpect(jsonPath("$.descricaoPedido").value(response.getDescricaoPedido()))
				.andExpect(jsonPath("$.status").value(response.getStatus()));		
	}
	@Test
	@Order(4)
	@DisplayName("Deve executar DELETE /api/v1/pedidos com sucesso (200).")
	public void testDeletePedido_Sucesso() throws Exception{
		
		//Gerando um ID para o pedido
		var id = UUID.randomUUID();
				
		var response = gerarPedidoResponse(id, gerarPedidoRequest());
						
		//Configurando o comportamento da camada de serviço
		when(pedidoService.inativarPedido(eq(id))).thenReturn(response);
						
		//Fazendo a requisição para a API
		mockMvc.perform(delete(endpoint + "/" + id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(response.getId().toString()))
				.andExpect(jsonPath("$.nomeCliente").value(response.getNomeCliente()))
				.andExpect(jsonPath("$.dataPedido").value(response.getDataPedido().toString()))
				.andExpect(jsonPath("$.valorPedido").value(response.getValorPedido().toString()))
				.andExpect(jsonPath("$.descricaoPedido").value(response.getDescricaoPedido()))
				.andExpect(jsonPath("$.status").value(response.getStatus()));		
	}
	@Test
	@Order(5)
	@DisplayName("Deve executar GET ALL /api/v1/pedidos com sucesso (200).")
	public void testGetAllPedido_Sucesso() throws Exception {
		
		var pedido1 = gerarPedidoResponse(UUID.randomUUID(), gerarPedidoRequest());
		var pedido2 = gerarPedidoResponse(UUID.randomUUID(), gerarPedidoRequest());
		List<PedidoResponseModel> pedidos = List.of(pedido1, pedido2);
		Page<PedidoResponseModel> page = new PageImpl<>(pedidos, PageRequest.of(0, 2), pedidos.size());
		when(pedidoService.consultarPedidos(ArgumentMatchers.any())).thenReturn(page);
		mockMvc.perform(get(endpoint)
				.param("page", "0")
		        .param("size", "2"))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.content.length()").value(pedidos.size()));	
	}
	@Test
	@Order(6)
	@DisplayName("Deve executar GET BY ID /api/v1/pedidos com sucesso (200).")
	public void testGetByIdPedido_Sucesso() throws Exception {
		var id = UUID.randomUUID();
		var pedido = gerarPedidoResponse(id, gerarPedidoRequest());
		when(pedidoService.obterPedidoPorId(eq(id))).thenReturn(pedido);
		mockMvc.perform(get(endpoint + "/" + id))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.id").value(pedido.getId().toString()))
				.andExpect(jsonPath("$.nomeCliente").value(pedido.getNomeCliente()))
				.andExpect(jsonPath("$.dataPedido").value(pedido.getDataPedido().toString()))
				.andExpect(jsonPath("$.valorPedido").value(pedido.getValorPedido().toString()))
				.andExpect(jsonPath("$.descricaoPedido").value(pedido.getDescricaoPedido()))
				.andExpect(jsonPath("$.status").value(pedido.getStatus()));		
	}
	
	private PedidoRequestModel gerarPedidoRequest() {
		
		var pedidoRequest = new PedidoRequestModel();
		
		pedidoRequest.setNomeCliente(faker.name().fullName());
		pedidoRequest.setDataPedido(LocalDate.now());
		pedidoRequest.setValorPedido(faker.number().randomDouble(2, 100, 1000));
		pedidoRequest.setDescricaoPedido(faker.commerce().department());
		
		var status = StatusPedido.values();
		var indice = new Random().nextInt(status.length);
		pedidoRequest.setStatus(status[indice].toString());		
		
		return pedidoRequest;
	}
	
	private PedidoResponseModel gerarPedidoResponse(UUID id, PedidoRequestModel request) {
		
		var pedidoResponse = new PedidoResponseModel();
		
		pedidoResponse.setId(id);
		pedidoResponse.setNomeCliente(request.getNomeCliente());
		pedidoResponse.setDataPedido(request.getDataPedido());
		pedidoResponse.setValorPedido(request.getValorPedido());
		pedidoResponse.setDescricaoPedido(request.getDescricaoPedido());		
		pedidoResponse.setStatus(request.getStatus());		
		
		return pedidoResponse;
	}
}


