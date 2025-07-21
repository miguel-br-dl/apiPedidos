package br.com.cotiinformatica.infrastructure.outbox;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OutboxMessage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String agregateType;  //ex: Pedido
	private String aggregateId;   //ex: "123 ou UUID do Pedido"
	private String type; 		  //ex: "PedidoCriado"
	private String payload;		  //ex: Dados a transmitir para a fila em JSON
	
	private boolean published = false;
	private LocalDateTime createdAt = LocalDateTime.now();
	private LocalDateTime transmittedAt; 
	

}
