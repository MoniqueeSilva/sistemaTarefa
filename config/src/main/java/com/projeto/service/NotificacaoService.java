package com.projeto.service;

import com.projeto.model.Notificacao;
import net.ravendb.client.documents.DocumentStore;
import org.springframework.stereotype.Service;

// Controla o estado da notificação no BD
@Service
public class NotificacaoService {

    private final DocumentStore store;

    public NotificacaoService(DocumentStore store){
        this.store = store;
    }

    public void prepare(Notificacao notificacao){
        try(var sessao = store.openSession()){
            notificacao.setStatus("PENDING");
            sessao.store(notificacao);
            sessao.saveChanges();
            System.out.println("NOTIFICACAO -> PREPARE (PENDING)");
        }
    }

    public void commit(String id){
        try(var sessao = store.openSession()){
            Notificacao n = sessao.load(Notificacao.class, id);
            if(n != null){
                n.setStatus("CONFIRMED");
                sessao.saveChanges();
                System.out.println("NOTIFICACAO -> COMMIT (CONFIRMED)");
            }
        }
    }

    public void rollback(String id){
        try(var sessao = store.openSession()){
            Notificacao n = sessao.load(Notificacao.class, id);
            if(n != null){
                n.setStatus("ABORTED");
                sessao.saveChanges();
                System.out.println("NOTIFICACAO -> ROLLBACK (ABORTED)");
            }
        }
    }
}
