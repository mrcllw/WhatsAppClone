package com.mrcllw.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.mrcllw.whatsapp.R;
import com.mrcllw.whatsapp.helper.Preferencias;

import java.util.HashMap;

public class ValidadorActivity extends AppCompatActivity {

    private EditText editCodValidacao;
    private Button buttonValidar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        editCodValidacao = (EditText) findViewById(R.id.editCodValidacao);
        buttonValidar = (Button) findViewById(R.id.buttonValidar);

        SimpleMaskFormatter validacaoSMF = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher validacaoMTW = new MaskTextWatcher(editCodValidacao, validacaoSMF);
        editCodValidacao.addTextChangedListener(validacaoMTW);

        buttonValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferencias preferencias = new Preferencias(ValidadorActivity.this);
                HashMap<String,String> usuario = preferencias.getUsuarioPreferencias();

                String tokenGerado = usuario.get("token");
                String tokenDigitado = editCodValidacao.getText().toString();

                if(tokenDigitado.equals(tokenGerado)){
                    Toast.makeText(ValidadorActivity.this, "Token validado!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ValidadorActivity.this, "Token não validado.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
