package com.mrcllw.whatsapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mrcllw.whatsapp.R;

public class MainActivity extends AppCompatActivity {

    private Button buttonSair;

    private FirebaseAuth firebaseAuth;
    //private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        //databaseReference = FirebaseDatabase.getInstance().getReference("teste");
        //databaseReference.setValue("teste");

        buttonSair = (Button) findViewById(R.id.buttonSair);

        buttonSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
