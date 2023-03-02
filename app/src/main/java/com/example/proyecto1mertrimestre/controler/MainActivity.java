package com.example.proyecto1mertrimestre.controler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.proyecto1mertrimestre.R;

/*
* El programa iniciará siempre desde esta activity. Siempre que entres tendrás que iniciar sesión
*
* La libreria que yo he elegido es esta: https://android-arsenal.com/details/1/8381
* Da formato a los textos (negrita, italica, subrrayado de color...)
* yo la he utilizado para hacer unos toast mas llamativos
* */
public class MainActivity extends AppCompatActivity {

    //ATRIBUTOS (visuales)
    private Button btnLogOn;
    private Button btnInitSesion;
    private ConstraintLayout cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogOn =(Button) findViewById(R.id.buttonRegistrarse);
        btnInitSesion =(Button) findViewById(R.id.buttonIniciar);
        cl=(ConstraintLayout) findViewById(R.id.clMain);

        //la proxima vez que vuelvas a entrar en la aplicacion se habran cargado las preferencias
        PreferencesActivity.loadPreferences(MainActivity.this, cl);

        //FUNCIONALIDAD DE LOS BOTONES, cargaremos distintas pantallas segun corresponda

        btnLogOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent i2 = new Intent(MainActivity.this, LogOnActivity.class);
                    startActivity(i2);

                }catch (Exception e){
                    Log.d("D", "onClick: "+e.toString());
                }
            }
        });

        btnInitSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3 = new Intent(MainActivity.this, InitSesionActivity.class);
                startActivity(i3);
            }
        });
    }
}