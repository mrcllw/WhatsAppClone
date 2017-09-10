package com.mrcllw.whatsapp.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mrcllw.whatsapp.R;
import com.mrcllw.whatsapp.application.FirebaseConfig;
import com.mrcllw.whatsapp.helper.Base64Custom;
import com.mrcllw.whatsapp.model.Usuario;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText editCadNome, editCadEmail, editCadSenha;
    private Button buttonCadastrar;

    private Usuario usuario;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        firebaseAuth = FirebaseConfig.getFirebaseAuth();

        editCadNome = (EditText) findViewById(R.id.editCadNome);
        editCadEmail = (EditText) findViewById(R.id.editCadEmail);
        editCadSenha = (EditText) findViewById(R.id.editCadSenha);
        buttonCadastrar = (Button) findViewById(R.id.buttonCadastrar);

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = new Usuario();
                usuario.setNome(editCadNome.getText().toString());
                usuario.setEmail(editCadEmail.getText().toString());
                usuario.setSenha(editCadSenha.getText().toString());
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario(){
        firebaseAuth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String id = Base64Custom.converterBase64(usuario.getEmail());
                    usuario.setId(id);
                    usuario.salvarUsuario();
                    finish();
                    Toast.makeText(CadastroUsuarioActivity.this, "Cadastrado!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(CadastroUsuarioActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
