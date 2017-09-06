package com.mrcllw.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.mrcllw.whatsapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editTelefone, editCodEstado, editCodPais, editNome;
    private Button buttonCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTelefone = (EditText) findViewById(R.id.editTelefone);
        editCodEstado = (EditText) findViewById(R.id.editCodEstado);
        editCodPais = (EditText) findViewById(R.id.editCodPais);
        editNome = (EditText) findViewById(R.id.editNome);
        buttonCadastrar = (Button) findViewById(R.id.buttonCadastrar);

        SimpleMaskFormatter telefoneSMF = new SimpleMaskFormatter("NNNNN-NNNN");
        SimpleMaskFormatter codEstadoSMF = new SimpleMaskFormatter("NN");
        SimpleMaskFormatter codPaisSMF = new SimpleMaskFormatter("+NN");

        MaskTextWatcher telefoneMTW = new MaskTextWatcher(editTelefone, telefoneSMF);
        MaskTextWatcher codEstadoMTW = new MaskTextWatcher(editCodEstado, codEstadoSMF);
        MaskTextWatcher codPaisMTW = new MaskTextWatcher(editCodPais, codPaisSMF);

        editTelefone.addTextChangedListener(telefoneMTW);
        editCodEstado.addTextChangedListener(codEstadoMTW);
        editCodPais.addTextChangedListener(codPaisMTW);
    }
}
