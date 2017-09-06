package com.mrcllw.whatsapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by mrcllw on 06/09/17.
 */

public class Preferencias {

    private Context context;
    private SharedPreferences sharedPreferences;

    private final String NOME_ARQUIVO = "whatsapp-preferencias";
    private final int MODE = 0;

    private final String CHAVE_NOME = "nome";
    private final String CHAVE_TELEFONE = "telefone";
    private final String CHAVE_TOKEN = "token";

    private SharedPreferences.Editor editor;

    public Preferencias (Context context) {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = sharedPreferences.edit();
    }

    public void salvarUsuarioPreferencias(String nome, String telefone, String token){
        editor.putString(CHAVE_NOME, nome);
        editor.putString(CHAVE_TELEFONE, telefone);
        editor.putString(CHAVE_TOKEN, token);
        editor.commit();
    }

    public HashMap<String, String> getUsuarioPreferencias(){
        HashMap<String, String> preferencias = new HashMap<>();
        preferencias.put(CHAVE_NOME, sharedPreferences.getString(CHAVE_NOME, null));
        preferencias.put(CHAVE_TELEFONE, sharedPreferences.getString(CHAVE_TELEFONE, null));
        preferencias.put(CHAVE_TOKEN, sharedPreferences.getString(CHAVE_TOKEN, null));
        return preferencias;
    }
}
