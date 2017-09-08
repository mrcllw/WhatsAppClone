package com.mrcllw.whatsapp.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mrcllw.whatsapp.R;
import com.mrcllw.whatsapp.adapter.TabAdapter;
import com.mrcllw.whatsapp.helper.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SlidingTabLayout stlTabs;
    private ViewPager viewPager;

    private FirebaseAuth firebaseAuth;
    //private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        //databaseReference = FirebaseDatabase.getInstance().getReference("teste");
        //databaseReference.setValue("teste");

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
}
