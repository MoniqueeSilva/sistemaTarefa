package com.projeto.model;

// Essa classe vai virar um dado JSON no banco é a estrutura da informação

public class Tarefa {
    private String id, descricao;
    private boolean concluida;
    private StatusTransacao status;

    // Construtor vazio para o Jackson/Spring poderem criar o objeto
    public Tarefa() {
    }

    public Tarefa(String id, String descricao, boolean concluida, StatusTransacao status){
        this.id = id;
        this.descricao = descricao;
        this.concluida = concluida;
        this.status = status;
    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getDescricao(){
        return descricao;
    }
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public boolean isConcluida(){
        return concluida;
    }
    public void setConcluida(boolean concluida){
        this.concluida = concluida;
    }

    public StatusTransacao getStatus(){
        return status;
    }
    public void setStatus(StatusTransacao status){
        this.status = status;
    }
}
