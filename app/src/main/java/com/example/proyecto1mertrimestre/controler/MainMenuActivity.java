package com.example.proyecto1mertrimestre.controler;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.proyecto1mertrimestre.R;

public class MainMenuActivity extends AppCompatActivity {

    //ATRIBUTOS (visuales)
    private Button btnTrap;
    private Button btnSee;
    private ConstraintLayout cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        btnTrap = (Button) findViewById(R.id.buttonPescar);
        btnSee = (Button) findViewById(R.id.buttonVerMisPeces);

        cl=(ConstraintLayout) findViewById(R.id.ConstLayOutPP);

        //la proxima vez que vuelvas a entrar en la aplicacion se habran cargado las preferencias
        PreferencesActivity.loadPreferences(MainMenuActivity.this, cl);

        //FUNCIONALIDAD DE LOS BOTONES, cargaremos distintas pantallas segun corresponda

        btnTrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i5 = new Intent(MainMenuActivity.this, TrapDuckActivity.class);
                startActivity(i5);
            }
        });

        btnSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i7 = new Intent(MainMenuActivity.this, SeeMyDucksActivity.class);
                startActivity(i7);
            }
        });

    }

    //aqui establezco el menu con el que vamos a acceder a la pantalla de preferencias.
    // Lo he pueste en un menu porque me he dado cuenta de que muchas aplicaciones lo hacen asi,
    // espero que sea facil de encontrar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.preference_simple_menu,menu);
        return true;
    }

    //aqui se da funcionalidad al item del menu, el de las preferencias
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId==R.id.ItemPreferencias){
            //accedo a la actividad de preferencias
            Intent i9 = new Intent(MainMenuActivity.this, PreferencesActivity.class);
            startActivity(i9);
        }
        return true;
    }
}
