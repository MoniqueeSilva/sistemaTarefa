package com.projeto.controller;

import com.projeto.model.Tarefa;
import com.projeto.service.TarefaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// O Controller serve para "conversar" com o postman
// Ele recebe requisições, lê JSON, chama o service e devolve resposta

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService service;

    public TarefaController(TarefaService service) {
        this.service = service;
    }

    @PostMapping
    public String criar(@RequestBody Tarefa tarefa) {
        service.salvar(tarefa);
        return "Tarefa criada!";
    }

    @GetMapping
    public List<Tarefa> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Tarefa buscar(@PathVariable String id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public String atualizar(@PathVariable String id, @RequestBody Tarefa tarefa) {
        service.atualizar(id, tarefa);
        return "Tarefa atualizada!";
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable String id) {
        service.deletar(id);
        return "Tarefa deletada!";
    }
}
