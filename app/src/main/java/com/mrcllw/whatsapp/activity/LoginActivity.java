package com.mrcllw.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.mrcllw.whatsapp.R;
import com.mrcllw.whatsapp.helper.Preferencias;

import java.util.HashMap;
import java.util.Random;

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

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeUsuario = editNome.getText().toString();
                String telefoneCompleto = editCodPais.getText().toString() +
                        editCodEstado.getText().toString() +
                        editTelefone.getText().toString();

                String telefoneSemChar = telefoneCompleto.replace("+", "").replace("-", "");

                Random random = new Random();
                int tokenRandom = random.nextInt(8999)+1000;
                String token = String.valueOf(tokenRandom);

                Preferencias preferencias = new Preferencias(getApplicationContext());
                preferencias.salvarUsuarioPreferencias(nomeUsuario, telefoneSemChar, token);

                String mensagem = "WhatsApp código de confirmação: " + token;
                telefoneSemChar = "8135";
                enviarSMS("+" + telefoneSemChar, mensagem);

                HashMap<String, String> usuarioPreferencias = preferencias.getUsuarioPreferencias();
            }
        });
    }

    private boolean enviarSMS(String telefone, String mensagem){
        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);
        } catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
}
