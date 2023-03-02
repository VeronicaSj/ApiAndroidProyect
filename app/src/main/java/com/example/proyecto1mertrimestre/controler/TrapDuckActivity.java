package com.example.proyecto1mertrimestre.controler;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.AsyncTask;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.curios.textformatter.FormatText;
import com.example.proyecto1mertrimestre.R;
import com.example.proyecto1mertrimestre.adapter.RecyclerAdapterTrapDuck;
import com.example.proyecto1mertrimestre.io.HttpConnectPatos;
import com.example.proyecto1mertrimestre.model.Duck;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrapDuckActivity extends AppCompatActivity {

    //ATRIBUTOS

    //atributos visuales
    private RecyclerView recyclerView;
    private RecyclerAdapterTrapDuck recAdapter;
    private ConstraintLayout cl;

    //atributos para el inicio de sesion
    private static ArrayList<Duck> duckList = new ArrayList<Duck>();
    private int duckArrayPositionToDelete;
    private androidx.appcompat.view.ActionMode mActionMode;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trap_duck);

        recyclerView=(RecyclerView) findViewById(R.id.recViewAtrapar);
        cl=(ConstraintLayout) findViewById(R.id.clTrapDuck);

        //inicializamos el recicler adapter y le damos tod o lo que necesita
        recAdapter= new RecyclerAdapterTrapDuck(this, duckList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(recAdapter);
        recyclerView.setLayoutManager(layoutManager);

        //la proxima vez que vuelvas a entrar en la aplicacion se habran cargado las preferencias
        PreferencesActivity.loadPreferences(TrapDuckActivity.this, cl);

        //obtenemos la lista de todos los patos
        new taskConnections().execute("GET", "/list");

        //le damos el listener al recicler adapter para que podamos tocar cada item
        recAdapter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //le decimos lo que vamos a hacer
                duckArrayPositionToDelete = recyclerView.getChildAdapterPosition(view);
                boolean res = false;

                //lanzamos el actionMenu
                if(mActionMode == null){
                    mActionMode = startSupportActionMode(mActionCallback);
                    res = true;
                }

                return res;
            }
        });
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
                    AlertDialog alertDialog = createAlertDialog("Eliminar",
                            "¿Estás seguro de que quieres  eliminar el pato?",
                            duckArrayPositionToDelete);
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

    //la siguiente clase sirve para obtener los datos de la api
    private class taskConnections extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            switch (strings[0]) {
                case "GET":
                    result = HttpConnectPatos.getRequest(strings[1]);
                    break;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                if(s != null){
                    Log.d("D","DATOS: "+s);

                    //lectura del JSON
                    /*
                        La estructura del json es esta:
                        {
                            gif_count:[]
                            gifs:[]
                            http:[]
                            image_count:[]
                            images:["num.jpg",...]-> esta es la lista que nos interesa
                        }
                    */
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("images");

                    //pasamos la lista JSON a nuestro ArrayList de java
                    for(int i=0; i<jsonArray.length(); i++){
                        String imageCode = "";
                        imageCode = jsonArray.getString(i);
                        duckList.add(new Duck(imageCode));
                    }

                    //notificcamos al adaptador que la info ha cambiado
                    recAdapter.notifyDataSetChanged();
                    Log.d("D","Array: "+ duckList.toString());
                }else{
                    myToast("Problema al cargar los datos");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

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
                //si pulsas si borras el pato de la lista
                duckList.remove(index);
                recAdapter.notifyDataSetChanged();
                myToast("¡Si este pato no es tuyo no sera de nadie!");
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //si pulsas que no no pasa nada, solo se vuelve a la lista
            }
        });

        //creamos
        return builder.create();
    }

    //funcion para parametrizar el mensaje del toast y ahorrar un poco de tiempo
    private void myToast(String str){
        //AQUI SE ESTA UTILIZANDO UNA BIBLIOTECA QUE PONE EL TEXTO BONITO
        Toast.makeText(this, FormatText.colorText("#"+str+"#","#f0dc26",true),Toast.LENGTH_SHORT).show();
    }
}
