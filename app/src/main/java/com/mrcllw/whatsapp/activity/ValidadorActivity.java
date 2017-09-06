package com.mrcllw.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.mrcllw.whatsapp.R;

public class ValidadorActivity extends AppCompatActivity {

    private EditText editCodValidacao;
    private Button buttonValidar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        editCodValidacao = (EditText) findViewById(R.id.editCodValidacao);
        buttonValidar = (Button) findViewById(R.id.buttonValidar);
    }
}
