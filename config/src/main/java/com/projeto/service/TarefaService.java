package com.projeto.service;

import java.util.List;
import com.projeto.model.Tarefa;
import net.ravendb.client.documents.DocumentStore;
import org.springframework.stereotype.Service;

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

    public void prepare(Tarefa tarefa) {
        try (var sessao = store.openSession()) {
            if (tarefa.getId() != null && sessao.advanced().exists(tarefa.getId())) {
                throw new RuntimeException("Tarefa já existe: " + tarefa.getId());
            }

            sessao.store(tarefa);
            sessao.saveChanges();
            
            System.out.println("TAREFA -> PREPARE (Salva no banco)");
        }
    }
    
    public void commit(String id) {
        System.out.println("TAREFA -> COMMIT (Confirmado ID: " + id + ")");
    }

    public void rollback(String id) {
        try (var sessao = store.openSession()) {
            Tarefa tarefa = sessao.load(Tarefa.class, id);
            
            if (tarefa != null) {
                sessao.delete(tarefa); 
                sessao.saveChanges();
                System.out.println("TAREFA -> ROLLBACK (Tarefa removida por erro na transação)");
            } else {
                System.out.println("TAREFA -> ROLLBACK (Nada a fazer, tarefa não encontrada)");
            }
        }
    }
}