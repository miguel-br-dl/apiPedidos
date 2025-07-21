package br.com.cotiinformatica.infrastructure.components;
import java.time.LocalDateTime;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import br.com.cotiinformatica.infrastructure.repositories.OutboxMessageRepository;
import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class PedidoCriadoProducer {
	private final RabbitTemplate rabbitTemplate;
	private final OutboxMessageRepository outboxMessageRepository;
	private final Queue queue;
	
	/*
	 * fixedDelay: 	Executando a cada X milisegundos desde o final da ultima execução
	 * fixedRate:	Executando a cada X milisegundos desde o início da ultima execução
	 */
	@Scheduled(fixedDelay = 60000)
	public void enviarPedidosCriados() {
		
		//Ler as mensagens na tabela de outbox que sejam do tipo PedidoCriado
		var pageable = PageRequest.of(0, 20); //ler os primeiros 20 registros
		var mensagens = outboxMessageRepository.find("PedidoCriado", pageable);
		
		try {
			
			//lendo as mensagens obtidas
			for(var mensagem : mensagens) {
				
				//enviando o conteudo JSON da mensagem para a fila
				rabbitTemplate.convertAndSend(queue.getName(), mensagem.getPayload());
				
				//atualizando o registro no banco de dados
				mensagem.setPublished(true);
				mensagem.setTransmittedAt(LocalDateTime.now());
				
				outboxMessageRepository.save(mensagem);
			}			
		}
		catch(Exception e) {
			e.printStackTrace(); //TODO gravar um log
		}
	}
}



