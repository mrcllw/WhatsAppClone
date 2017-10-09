package com.mrcllw.whatsapp.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrcllw.whatsapp.R;
import com.mrcllw.whatsapp.model.Contato;
import com.mrcllw.whatsapp.utils.ImageCircle;

import java.util.List;

/**
 * Created by Marcello on 08/10/2017.
 */

public class RecyclerViewContatosAdapter extends RecyclerView.Adapter<RecyclerViewContatosAdapter.ViewHolder> {

    private List<Contato> contatos;
    private Activity activity;

    public RecyclerViewContatosAdapter(Activity activity, List<Contato> contatos){
        this.activity = activity;
        this.contatos = contatos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNomeContato, textViewDescricaoContato;
        ImageView fotoContato;

        public ViewHolder(View view){
            super(view);
            textViewNomeContato = (TextView) view.findViewById(R.id.nomeContato);
            textViewDescricaoContato = (TextView) view.findViewById(R.id.descricaoContato);
            fotoContato = (ImageView) view.findViewById(R.id.fotoContato);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_contatos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Contato contato = getItem(position);
        holder.textViewNomeContato.setText(contato.getNome());
        //holder.textViewDescricaoContato.setText(contato.getDescricao());
        holder.textViewDescricaoContato.setText("Descrição");
        Bitmap bm;
        //if(contato.getCaminhoFoto() != null){
        if(false){
            //bm = BitmapFactory.decodeFile(contato.getCaminhoFoto());
        } else {
            bm = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_no_image);
        }
        holder.fotoContato.setImageBitmap(ImageCircle.crop(bm));
    }

    public Contato getItem(int position){
        return contatos.get(position);
    }

    @Override
    public int getItemCount(){
        return contatos.size();
    }
}
