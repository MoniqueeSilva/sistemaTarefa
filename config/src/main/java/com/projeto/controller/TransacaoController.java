package com.projeto.controller;

import com.projeto.model.Tarefa;
import com.projeto.service.TransacaoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final TransacaoService service;

    public TransacaoController(TransacaoService service) {
        this.service = service;
    }

    @PostMapping
    public String executar(@RequestBody Tarefa tarefa,
                           @RequestParam(defaultValue = "false") boolean erro) {

        return service.executar2PC(tarefa, erro);
    }
}
