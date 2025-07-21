package br.com.cotiinformatica.infrastructure.repositories;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.infrastructure.outbox.OutboxMessage;

@Repository
public interface OutboxMessageRepository extends JpaRepository<OutboxMessage, Long> {
	/*
	 * Método de consulta com JPQL que retorne todos os registros
	 * de mensagens que não foram transmitidos de um determinado evento
	 */
	@Query("""
			SELECT o FROM OutboxMessage o
			WHERE o.published = false
			  AND o.type = :type
			ORDER BY o.createdAt ASC
			""")
	List<OutboxMessage> find(@Param("type") String type, Pageable pageable);
}



