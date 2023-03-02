package com.example.proyecto1mertrimestre.controler;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.curios.textformatter.FormatText;
import com.example.proyecto1mertrimestre.R;
import com.example.proyecto1mertrimestre.io.BDaccess;
import com.example.proyecto1mertrimestre.model.User;

public class LogOnActivity extends AppCompatActivity {

    //ATRIBUTOS

    //atrivutos visuales
    private EditText textUsuario;
    private EditText textPassword;
    private Button btnRegistrarse;
    private ConstraintLayout cl;

    //atributos para registrarse
    private BDaccess mBD;
    private User user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        mBD= new BDaccess(this);

        textUsuario    = (EditText) findViewById(R.id.editTextUsuario);
        textPassword     = (EditText) findViewById(R.id.editTextPassword);
        btnRegistrarse    = (Button) findViewById(R.id.buttonAceptRegistrarse);

        cl=(ConstraintLayout) findViewById(R.id.clRegistrarse);

        //la proxima vez que vuelvas a entrar en la aplicacion se habran cargado las preferencias
        PreferencesActivity.loadPreferences(LogOnActivity.this, cl);

        //damos funcionalidad al boton con un listener
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre=textUsuario.getText().toString();
                String contrasenia=textPassword.getText().toString();

                user = new User(nombre, contrasenia);
                boolean parametrosAptos=false;

                //si los parametros son aptos continuamos con los chequeos
                if (user.obtenerMsg().equals("valido")){
                    parametrosAptos=true;
                }else{
                    miToast(user.obtenerMsg());
                }

                //si los dos parametros son aptos, miramos en la base de datos si el nombre existe
                if(parametrosAptos){
                    //aqui habria que poner el codigo que comprueba en la base de datos
                    if(mBD.userExist(nombre)){
                        miToast("Error! el nombre ya exite");
                    }else{
                        if(mBD.insertUser(nombre,contrasenia) != -1){
                            //comunicamos al nombre que ya se está registrando y que ha ido bien
                            miToast("REGISTRANDOTE");
                        }else{
                            miToast("Error! no se pudo registrar");
                        }
                    }
                    //finalmente volvemos a la pantalla principal para iniciar sesion
                    finish();
                }
            }
        });
    }

    //funcion para parametrizar el mensaje del toast y ahorrar un poco de tiempo
    private void miToast(String msg){
        //AQUI SE ESTA UTILIZANDO UNA BIBLIOTECA QUE PONE EL TEXTO BONITO
        Toast.makeText(LogOnActivity.this, FormatText.colorText("#"+msg+"#","#f0dc26",true), Toast.LENGTH_SHORT).show();
    }
}
