package com.projeto.model;

// Essa classe vai virar um dado JSON no banco é a estrutura da informação

public class Tarefa {
    private String id, descricao;
    private boolean concluida;

    public Tarefa(String id, String descricao, boolean concluida){
        this.id = id;
        this.descricao = descricao;
        this.concluida = concluida;
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
}
