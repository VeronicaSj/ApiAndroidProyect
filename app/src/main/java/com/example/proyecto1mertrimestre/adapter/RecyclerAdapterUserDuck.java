package com.example.proyecto1mertrimestre.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.proyecto1mertrimestre.R;
import com.example.proyecto1mertrimestre.model.Duck;

import java.util.List;

public class RecyclerAdapterUserDuck extends RecyclerView.Adapter<RecyclerAdapterUserDuck.RecyclerHolder> {

    //ATRIBUTOS
    private List<Duck> duckList1;//los datos para mostrar
    private Context context;//necesitamos el context para el glide
    private AdapterView.OnLongClickListener onLongClickListener;

    //constructor
    public RecyclerAdapterUserDuck(List<Duck> duckList, Context context){
        this.duckList1 =duckList;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_adap_ver_patos,parent , false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);

        //activamos el listener
        view.setOnLongClickListener(onLongClickListener);

        return recyclerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        Duck duck = duckList1.get(position);

        //Configuración del CircularProgressDrawable
        CircularProgressDrawable progressDrawable= new CircularProgressDrawable(context);
        progressDrawable.setStrokeWidth(10f);
        progressDrawable.setStyle(CircularProgressDrawable.LARGE);
        progressDrawable.setCenterRadius(30f);
        progressDrawable.start();

        //cargamos las imagenes desde el enlace
        Glide.with(context)
                .load("https://random-d.uk/api/"+duck.getImagen())
                .placeholder(progressDrawable)
                .error(R.drawable.notfound)
                .into(holder.imgPato);
    }

    //setter del listener
    public void setOnLongClickListener(View.OnLongClickListener listener){
        this.onLongClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return duckList1.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        //la parte que se repite en este recicler view es solo una foto
        ImageView imgPato;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            imgPato= (ImageView) itemView.findViewById(R.id.imageViewVer);
        }
    }
}
