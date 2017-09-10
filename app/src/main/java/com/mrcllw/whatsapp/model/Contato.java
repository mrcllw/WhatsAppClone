package com.mrcllw.whatsapp.model;

/**
 * Created by mrcllw on 10/09/17.
 */

public class Contato {

    private String id;
    private String nome;
    private String email;

    public Contato(){
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
