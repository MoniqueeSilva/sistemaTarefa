package com.projeto.service;

import com.projeto.model.Tarefa;
import com.projeto.model.Notificacao;
import org.springframework.stereotype.Service;

@Service
public class TransacaoService {

    private final TarefaService tarefaService;
    private final NotificacaoService notificacaoService;

    public TransacaoService(TarefaService tarefaService, NotificacaoService notificacaoService){
        this.tarefaService = tarefaService;
        this.notificacaoService = notificacaoService;
    }

    public String executar2PC(Tarefa tarefa, boolean erro){

        Notificacao notificacao = new Notificacao();
        notificacao.setMensagem("Nova tarefa: " + tarefa.getDescricao());

        try{

            // =========================
            // FASE 1 - PREPARE
            // =========================
            System.out.println("=== FASE 1: PREPARE ===");

            tarefaService.prepare(tarefa);
            notificacao.setId("notificacoes/" + java.util.UUID.randomUUID());
            notificacaoService.prepare(notificacao);

            if(erro){
                throw new RuntimeException("Erro simulado!");
            }

            // =========================
            // FASE 2 - COMMIT
            // =========================
            System.out.println("=== FASE 2: COMMIT ===");

            tarefaService.commit(tarefa.getId());
            notificacaoService.commit(notificacao.getId());

            return "Transação confirmada!";

        } catch(Exception e){

            // =========================
            // ROLLBACK
            // =========================
            System.out.println("=== ROLLBACK ===");

            tarefaService.rollback(tarefa.getId());
            notificacaoService.rollback(notificacao.getId());

            return "Transação abortada!";
        }
    }
}
