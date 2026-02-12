package com.projeto.model;

public class Notificacao {
    private String id, mensagem, status;

    public Notificacao(){}

    public Notificacao(String id, String mensagem, String status){
        this.id = id;
        this.mensagem = mensagem;
        this.status = status;
    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getMensagem(){
        return mensagem;
    }
    public void setMensagem(String mensagem){
        this.mensagem = mensagem;
    }

    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }

}
