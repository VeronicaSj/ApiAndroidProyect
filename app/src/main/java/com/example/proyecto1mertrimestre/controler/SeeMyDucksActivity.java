package com.example.proyecto1mertrimestre.controler;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.curios.textformatter.FormatText;
import com.example.proyecto1mertrimestre.R;
import com.example.proyecto1mertrimestre.adapter.RecyclerAdapterUserDuck;
import com.example.proyecto1mertrimestre.io.BDaccess;
import com.example.proyecto1mertrimestre.model.Duck;

import java.util.ArrayList;

public class SeeMyDucksActivity extends AppCompatActivity {

    //ATRIBUTOS

    //atributos visuales
    private RecyclerView recyclerView;
    private RecyclerAdapterUserDuck recAdapter;
    private ConstraintLayout cl;

    //atributos para el inicio de sesion
    private BDaccess bDaccess;
    private int duckArrayPositionToSetFree;
    private ArrayList<Duck> duckList;
    private androidx.appcompat.view.ActionMode mActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_patos);

        try {
            recyclerView = (RecyclerView) findViewById(R.id.recViewVer);
            cl=(ConstraintLayout) findViewById(R.id.clayout);
            bDaccess = new BDaccess(this);

            //obtenemos todos los patos que nuestro usuario ha guardado
            duckList=bDaccess.getAllDucks(InitSesionActivity.getUser());
            Log.d("D","LISTA: " +duckList.toString());

            //inicializamos el recicler adapter y le damos tod o lo que necesita
            recAdapter = new RecyclerAdapterUserDuck(duckList,this);
            GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
            recyclerView.setAdapter(recAdapter);
            recyclerView.setLayoutManager(layoutManager);

            //la proxima vez que vuelvas a entrar en la aplicacion se habran cargado las preferencias
            PreferencesActivity.loadPreferences(SeeMyDucksActivity.this, cl);

            //le damos el listener al recicler adapter para que podamos tocar cada item
            recAdapter.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //le decimos lo que vamos a hacer
                    duckArrayPositionToSetFree = recyclerView.getChildAdapterPosition(view);
                    boolean res = false;

                    //lanzamos el actionMenu
                    if(mActionMode == null){
                        mActionMode = startSupportActionMode(mActionCallback);
                        res = true;
                    }
                    return res;
                }
            });

        }catch (Exception ex){
            Log.d("D", "onCreate: "+ex.toString());
        }
    }

    //hacemos la funcion dde llamada al actionMenu
    private ActionMode.Callback mActionCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.long_clicked_duck_action_menu, menu);
            mode.setTitle("Action Menu");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        //cuando pulsemos el item vamos a mostrar un alertDialog
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int itemId = item.getItemId();
            switch (itemId) {
                case R.id.act_kill:
                    AlertDialog alertDialog = createAlertDialog("Liberar",
                            "¿Quieres liberar el pato?",
                            duckArrayPositionToSetFree);
                    alertDialog.show();
                    mode.finish();
                    break;
            }

            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

    //funcion que nos ayuda a crear el alert dialog de esta pantalla
    public AlertDialog createAlertDialog(String titulo, String mensaje, int index){

        //creamos el creador y pasamos los datos
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje)
                .setTitle(titulo);

        //damos funcionalidad al boton de "si"
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                bDaccess.deleteDuck(duckList.get(index)); //lo borramos para futuros usos de la app
                duckList.remove(index); //lo borramos para el uso actual
                recAdapter.notifyDataSetChanged();
                myToast("El pato sera feliz 'pa-to' la vida");
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //si pulsas que no, no pasa nada, solo se vuelve a la lista
            }
        });

        //creamos
        return builder.create();
    }

    //funcion para parametrizar el mensaje del toast y ahorrar un poco de tiempo
    private void myToast(String str){
        //AQUI SE ESTA UTILIZANDO UNA BIBLIOTECA QUE PONE EL TEXTO BONITO
        Toast.makeText(this, FormatText.colorText("#"+str+"#","#f0dc26",true),Toast.LENGTH_LONG).show();
    }
}