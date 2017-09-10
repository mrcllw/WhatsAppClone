package com.mrcllw.whatsapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mrcllw.whatsapp.R;
import com.mrcllw.whatsapp.application.FirebaseConfig;
import com.mrcllw.whatsapp.helper.Base64Custom;
import com.mrcllw.whatsapp.helper.Preferencias;
import com.mrcllw.whatsapp.model.Usuario;


public class LoginActivity extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private TextView textCadastrar;
    private Button buttonLogar;

    private Usuario usuario;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseConfig.getFirebaseAuth();
        verificarUsuarioLogado();

        editEmail = (EditText) findViewById(R.id.editEmail);
        editSenha = (EditText) findViewById(R.id.editSenha);
        textCadastrar = (TextView) findViewById(R.id.textCadastrar);
        buttonLogar = (Button) findViewById(R.id.buttonLogar);

        textCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
                startActivity(intent);
            }
        });

        buttonLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = new Usuario();
                usuario.setEmail(editEmail.getText().toString());
                usuario.setSenha(editSenha.getText().toString());
                validarLogin();
            }
        });
    }

    private void validarLogin(){
        firebaseAuth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Conectado!", Toast.LENGTH_LONG).show();

                    Preferencias preferencias = new Preferencias(LoginActivity.this);
                    String id = Base64Custom.converterBase64(usuario.getEmail());
                    preferencias.salvarDados(id);

                    abrirTelaPrincipal();
                } else {
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void verificarUsuarioLogado(){
        if(firebaseAuth.getCurrentUser() != null){
            abrirTelaPrincipal();
        }
    }

    private void abrirTelaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
