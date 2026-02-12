package com.projeto.service;

import java.util.List;

import com.projeto.model.StatusTransacao;
import com.projeto.model.Tarefa;
import net.ravendb.client.documents.DocumentStore;
import org.springframework.stereotype.Service;

// O Service faz a "lógica de negócio", valida a lógica do sistema, aplica regras
// Controla o fluxo que ocorre no controller, serve de consulta de dados no banco 

@Service
public class TarefaService {
    private final DocumentStore store;

    public TarefaService(DocumentStore store){
        this.store = store;
    }

    public void salvar(Tarefa tarefa){
        try(var sessao = store.openSession()){
            sessao.store(tarefa);
            sessao.saveChanges();
        }
    }

    public List<Tarefa> listar(){
        try (var sessao = store.openSession()) {
            return sessao.query(Tarefa.class).toList();
        }
    }

    public Tarefa buscarPorId(String id) {
        try (var sessao = store.openSession()) {
            return sessao.load(Tarefa.class, id);
        }
    }

    public void atualizar(String id, Tarefa nova) {
        try (var sessao = store.openSession()) {
            Tarefa tarefa = sessao.load(Tarefa.class, id);
            if (tarefa != null) {
                tarefa.setDescricao(nova.getDescricao());
                tarefa.setConcluida(nova.isConcluida());
                sessao.saveChanges();
            }
        }
    }

    public void deletar(String id) {
        try (var sessao = store.openSession()) {
            Tarefa tarefa = sessao.load(Tarefa.class, id);
            if (tarefa != null) {
                sessao.delete(tarefa);
                sessao.saveChanges();
            }
        }
    }

    public void prepare(Tarefa tarefa){
        try(var sessao = store.openSession()){
            tarefa.setStatus(StatusTransacao.PEDING);
            sessao.store(tarefa);
            sessao.saveChanges();
            System.out.println("TAREFA -> PREPARE (PENDING)");
        }
    }

    public void commit(String id){
        try(var sessao = store.openSession()){
            Tarefa tarefa = sessao.load(Tarefa.class, id);
            if(tarefa != null){
                tarefa.setStatus(StatusTransacao.CONFIRMED);
                sessao.saveChanges();
                System.out.println("TAREFA -> COMMIT (CONFIRMED)");
            }
        }
    }

    public void rollback(String id){
        try(var sessao = store.openSession()){
            Tarefa tarefa = sessao.load(Tarefa.class, id);
            if(tarefa != null){
                tarefa.setStatus(StatusTransacao.ABORTED);
                sessao.saveChanges();
                System.out.println("TAREFA -> ROLLBACK (ABORTED)");
            }
        }
    }
}