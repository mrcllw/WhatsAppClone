package com.mrcllw.whatsapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mrcllw.whatsapp.R;
import com.mrcllw.whatsapp.adapter.TabAdapter;
import com.mrcllw.whatsapp.application.FirebaseConfig;
import com.mrcllw.whatsapp.helper.Base64Custom;
import com.mrcllw.whatsapp.helper.Preferencias;
import com.mrcllw.whatsapp.helper.SlidingTabLayout;
import com.mrcllw.whatsapp.model.Contato;
import com.mrcllw.whatsapp.model.Usuario;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SlidingTabLayout stlTabs;
    private ViewPager viewPager;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseConfig.getFirebaseAuth();
        firebaseDatabase = FirebaseConfig.getFirebaseDatabaseReference();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        stlTabs = (SlidingTabLayout) findViewById(R.id.stlTabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        toolbar.setTitle("WhatsApp");
        setSupportActionBar(toolbar);

        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        stlTabs.setDistributeEvenly(true);
        stlTabs.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorAccent));
        stlTabs.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemSair:
                deslogarUsuario();
                return true;
            case R.id.itemConfiguracoes :
                return true;
            case R.id.itemAdiciona :
                adicionarContato();
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    private void deslogarUsuario(){
        firebaseAuth.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void adicionarContato(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Novo contato");
        alertDialog.setMessage("E-mail do usuario");
        alertDialog.setCancelable(false);

        final EditText emailContato = new EditText(this);
        alertDialog.setView(emailContato);

        alertDialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String emailDigitado = emailContato.getText().toString();
                if(emailDigitado.isEmpty()){
                    Toast.makeText(MainActivity.this, "Preencha o e-mail", Toast.LENGTH_LONG).show();
                } else {
                    final String idContato = Base64Custom.converterBase64(emailDigitado);

                    DatabaseReference databaseReferenceUsuarios = firebaseDatabase.child("usuarios").child(idContato);
                    databaseReferenceUsuarios.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue() != null){
                                Usuario usuarioContato = new Usuario();
                                usuarioContato = dataSnapshot.getValue(Usuario.class);

                                Preferencias preferencias = new Preferencias(MainActivity.this);
                                String idUsuarioLogado = preferencias.getId();

                                Contato contato = new Contato();
                                contato.setId(idContato);
                                contato.setEmail(usuarioContato.getEmail());
                                contato.setNome(usuarioContato.getNome());

                                DatabaseReference databaseReferenceContatos = firebaseDatabase
                                        .child("contatos")
                                        .child(idUsuarioLogado)
                                        .child(idContato);

                                databaseReferenceContatos.setValue(contato);
                                Toast.makeText(MainActivity.this, "Contato adicionado!", Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(MainActivity.this, "Contato não cadastrado", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertDialog.create();
        alertDialog.show();
    }
}
