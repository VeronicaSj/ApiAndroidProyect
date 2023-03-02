package com.example.proyecto1mertrimestre.controler;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

import com.example.proyecto1mertrimestre.R;
import com.example.proyecto1mertrimestre.fragment.PreferenciasFragment;
import com.example.proyecto1mertrimestre.io.BDaccess;

public class PreferencesActivity extends AppCompatActivity {
    private ConstraintLayout cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        cl=(ConstraintLayout) findViewById(R.id.contenedorPreferencias);

        //la proxima vez que vuelvas a entrar en la aplicacion se habran cargado las preferencias
        loadPreferences(PreferencesActivity.this,cl);

        //reemplazo el contenedor por una instancia de la clase SettingFragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedorPreferencias, new PreferenciasFragment())
                .commit();
    }


    //funcion que carga las preferencias alla donde la llames
    public static void loadPreferences(Context context, ConstraintLayout bground){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean nightModeOn = sharedPreferences.getBoolean("sp_modo_nocturno", false);
        if (nightModeOn){
            bground.setBackgroundColor(Color.DKGRAY);
        }
    }
}
