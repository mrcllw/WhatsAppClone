package com.mrcllw.whatsapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mrcllw on 06/09/17.
 */

public class Preferencias {

    private Context context;
    private SharedPreferences sharedPreferences;

    private final String NOME_ARQUIVO = "whatsapp-preferencias";
    private final int MODE = 0;

    private final String CHAVE_ID = "idUsuario";

    private SharedPreferences.Editor editor;

    public Preferencias (Context context) {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = sharedPreferences.edit();
    }

    public void salvarDados(String id){
        editor.putString(CHAVE_ID, id);
        editor.commit();
    }

    public String getId(){
        return sharedPreferences.getString(CHAVE_ID, null);
    }
}
