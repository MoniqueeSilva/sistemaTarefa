package com.projeto.controller;

import com.projeto.model.Notificacao;
import com.projeto.service.NotificacaoService;
import net.ravendb.client.documents.DocumentStore;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Gerencia as requisições relacionadas às notificações, por meio da API
@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;
    private final DocumentStore store;

    public NotificacaoController(NotificacaoService notificacaoService, DocumentStore store) {
        this.notificacaoService = notificacaoService;
        this.store = store;
    }

    //Criar notificação (PREPARE)
    @PostMapping
    public String criar(@RequestBody Notificacao notificacao) {

        notificacao.setId("notificacoes/" + java.util.UUID.randomUUID());
        notificacaoService.prepare(notificacao);

        return "Notificação criada com status PENDING";
    }

    //Confirmar (COMMIT)
    @PutMapping("/{id}/confirmar")
    public String confirmar(@PathVariable String id) {

        notificacaoService.commit(id);
        return "Notificação confirmada!";
    }

    //Cancelar (ROLLBACK)
    @PutMapping("/{id}/cancelar")
    public String cancelar(@PathVariable String id) {

        notificacaoService.rollback(id);
        return "Notificação cancelada!";
    }

    //Listar todas
    @GetMapping
    public List<Notificacao> listar() {
        try (var sessao = store.openSession()) {
            return sessao.query(Notificacao.class).toList();
        }
    }

    //Buscar por ID
    @GetMapping("/{id}")
    public Notificacao buscarPorId(@PathVariable String id) {
        try (var sessao = store.openSession()) {
            return sessao.load(Notificacao.class, id);
        }
    }
}
