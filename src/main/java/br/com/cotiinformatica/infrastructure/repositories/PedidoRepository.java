package br.com.cotiinformatica.infrastructure.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.domain.entities.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID>{

	@Query("""
			SELECT p from Pedido p
			WHERE p.id = :id
			and p.ativo = true
			""")
	Optional<Pedido> findByIdAndAtivo(@Param("id") UUID id);
	
	@Query("""
			SELECT p from Pedido p
			WHERE p.ativo = true
			ORDER BY p.dataPedido DESC
			""")
	Page<Pedido> findAtivos(Pageable pageable);
	
}
