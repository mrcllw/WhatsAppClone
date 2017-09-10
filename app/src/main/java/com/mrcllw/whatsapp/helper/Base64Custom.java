package com.mrcllw.whatsapp.helper;

import android.util.Base64;

/**
 * Created by mrcllw on 10/09/17.
 */

public class Base64Custom {

    public static String converterBase64(String texto){
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).trim();
    }

    public static String decodificarBase64(String texto){
        byte[] decodificado = Base64.decode(texto, Base64.DEFAULT);
        return new String(decodificado);
    }
}
