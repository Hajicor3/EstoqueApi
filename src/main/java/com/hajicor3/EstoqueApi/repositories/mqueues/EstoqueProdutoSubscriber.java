package com.hajicor3.EstoqueApi.repositories.mqueues;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hajicor3.EstoqueApi.services.MovimentacaoService;
import com.hajicor3.EstoqueApi.services.exceptions.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EstoqueProdutoSubscriber {
	
	@Autowired
	private MovimentacaoService movimentacaoService;
	
	@RabbitListener(queues = "${mq.queue.produto-estoque-queue}")
	public void receberSolicitacaoCancelamentoMovimentacao(@Payload String payload) {
		log.info("Mensagem recebida: {}",payload);
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			Long id = mapper.readValue(payload, Long.class);
			movimentacaoService.cancelarMovimentacao(id);
		}
		catch(NumberFormatException e) {
			log.error("Erro ao converter o id da mensagem: " + Long.parseLong(payload) + e);
		}
		catch(ResourceNotFoundException e) {
			log.warn("Movimentação não encontrada para o ID: " + Long.parseLong(payload));
		}
		catch(Exception e){
			log.error("Erro inesperado ao processar a solicitação de cancelamento de movimentação", e);
		}
	}
}
