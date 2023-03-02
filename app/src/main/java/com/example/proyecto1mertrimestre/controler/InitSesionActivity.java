package com.example.proyecto1mertrimestre.controler;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class InitSesionActivity extends AppCompatActivity {

    //ATRIBUTOS

    //atributos visuales
    private EditText textUsuario;
    private EditText textPassword;
    private Button btnIniciarSesion;
    private ConstraintLayout cl;

    //atributos para el inicio de sesion
    private BDaccess mBD;
    private static User user=new User("","");//la info de inicio de sesion
                                                            // debe permanecer durante toda la sesion

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        mBD= new BDaccess(this);

        textUsuario    = (EditText) findViewById(R.id.editTextUserIS);
        textPassword     = (EditText) findViewById(R.id.editTextPasswordIS);
        btnIniciarSesion    = (Button) findViewById(R.id.buttonIniciar);

        cl=(ConstraintLayout) findViewById(R.id.clIniciarSesion);

        //la proxima vez que vuelvas a entrar en la aplicacion se habran cargado las preferencias
        PreferencesActivity.loadPreferences(InitSesionActivity.this, cl);

        //damos funcionalidad al boton con un listener
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //inicializacion y declaracion de variables
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
                    if(mBD.userExist(user.getNombre())){
                        String contraseniaCorrect=mBD.getPassword(user.getNombre());
                        if(contraseniaCorrect.equals(user.getContrasenia())){
                            //comunicamos al nombre que ya se está registrando y que ha ido bien
                            miToast("INICIANDO SESION");
                            //vamos a la propia aplicacion
                            Intent i4 = new Intent(InitSesionActivity.this, MainMenuActivity.class);
                            startActivity(i4);
                        }else{
                            miToast("Error! la contrasenia no coincide");
                        }
                    }else{
                        miToast("Error! el usuario no existe");
                    }

                    //finalmente restablecemos los campos
                    textUsuario.setText("");
                    textPassword.setText("");
                }
            }
        });
    }

    //funcion para parametrizar el mensaje del toast y ahorrar un poco de tiempo
    private void miToast(String msg){
        //AQUI SE ESTA UTILIZANDO UNA BIBLIOTECA QUE PONE EL TEXTO BONITO
        Toast.makeText(InitSesionActivity.this, FormatText.colorText("#"+msg+"#","#f0dc26",true), Toast.LENGTH_SHORT).show();
    }

    //funcion para obtener el usuario desde otras pantallas
    public static User getUser(){
        return user;
    }
}
