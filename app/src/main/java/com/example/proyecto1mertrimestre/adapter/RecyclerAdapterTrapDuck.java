package com.example.proyecto1mertrimestre.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.example.proyecto1mertrimestre.R;
import com.example.proyecto1mertrimestre.controler.InitSesionActivity;
import com.example.proyecto1mertrimestre.io.BDaccess;
import com.example.proyecto1mertrimestre.model.Duck;

import java.util.ArrayList;

import com.bumptech.glide.Glide;

public class RecyclerAdapterTrapDuck extends RecyclerView.Adapter<RecyclerAdapterTrapDuck.RecyclerHolder> {

    //ATRIBUTOS
    private ArrayList<Duck> duckList;//los datos para mostrar
    private Context context;//necesitamos el context para el listener y glide
    private BDaccess mBD;//vamos a guardar el codigo del pato para luego cargar la imagen desde el codigo
    private AdapterView.OnLongClickListener onLongClickListener;

    //CONSTRUCTOR
    public RecyclerAdapterTrapDuck(Context context, ArrayList<Duck> duckList){
        this.duckList =duckList;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_adap_trap,parent , false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);

        //activamos el listener
        view.setOnLongClickListener(onLongClickListener);

        return recyclerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        Duck duckLink = duckList.get(position);

        //Configuración del CircularProgressDrawable
        CircularProgressDrawable progressDrawable= new CircularProgressDrawable(context);
        progressDrawable.setStrokeWidth(10f);
        progressDrawable.setStyle(CircularProgressDrawable.LARGE);
        progressDrawable.setCenterRadius(30f);
        progressDrawable.start();

        //cargamos las imagenes desde el enlace
        Glide.with(context)
                .load("https://random-d.uk/api/"+duckLink.getImagen())
                .placeholder(progressDrawable)
                .error(R.drawable.notfound)
                .into(holder.imgDuck);

        //este recicler view tiene un boton para guardar el pato que nos guste
        //activamos el listener y le damos funcionalidad
        holder.btnTrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBD= new BDaccess(context);
                mBD.insertUserDuck(InitSesionActivity.getUser(),duckLink);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.duckList.size();
    }

    //setter del listener
    public void setOnLongClickListener(View.OnLongClickListener listener){
        this.onLongClickListener = listener;
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        ImageView imgDuck;
        Button btnTrap;
        View itemView;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            imgDuck = (ImageView) itemView.findViewById(R.id.imageViewTrap);
            btnTrap =(Button) itemView.findViewById(R.id.buttonTrap);
            this.itemView=itemView;
        }
    }
}