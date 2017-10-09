package com.mrcllw.whatsapp.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mrcllw.whatsapp.R;
import com.mrcllw.whatsapp.adapter.RecyclerViewContatosAdapter;
import com.mrcllw.whatsapp.application.FirebaseConfig;
import com.mrcllw.whatsapp.helper.Preferencias;
import com.mrcllw.whatsapp.model.Contato;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {

    private RecyclerView recyclerViewContatos;
    private RecyclerViewContatosAdapter adapter;
    private ArrayList<Contato> contatos;
    private DatabaseReference databaseReference;
    private Paint p = new Paint();

    public ContatosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        contatos = new ArrayList<>();

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_contatos, container, false);

        recyclerViewContatos = (RecyclerView) view.findViewById(R.id.listViewContatos);
        recyclerViewContatos.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewContatos.setLayoutManager(layoutManager);

        adapter = new RecyclerViewContatosAdapter(this.getActivity(), contatos);
        recyclerViewContatos.setAdapter(adapter);

        Preferencias preferencias = new Preferencias(getActivity());
        databaseReference = FirebaseConfig.getFirebaseDatabaseReference().child("contatos").child(preferencias.getId());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contatos.clear();
                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    Contato contato = dados.getValue(Contato.class);
                    contatos.add(contato);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        initSwipe();

        return view;
    }


    private void initSwipe(){
        final ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){
            @Override
            public boolean onMove(RecyclerView recycleView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target){
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction){
                int position = viewHolder.getAdapterPosition();

                if(direction == ItemTouchHelper.LEFT){

                } else {

                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){
                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                    View itemView = viewHolder.itemView;
                    float heigth = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = heigth / 3;

                    if(dX > 0){
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_edit_white);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewContatos);
    }

}
