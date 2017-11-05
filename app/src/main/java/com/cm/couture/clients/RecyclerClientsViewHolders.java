package com.cm.couture.clients;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cm.couture.R;

public class RecyclerClientsViewHolders extends RecyclerView.ViewHolder{

    public TextView clientNom=null;
    public TextView clientTelephone=null;
    //public TextView clientSexe=null;
    public de.hdodenhof.circleimageview.CircleImageView clientPhoto=null;

    public RecyclerClientsViewHolders(View itemView) {
        super(itemView);

        clientNom = (TextView)itemView.findViewById(R.id.client_nom);
        clientTelephone = (TextView)itemView.findViewById(R.id.client_telephone);
        //clientSexe = (TextView)itemView.findViewById(R.id.client_sexe);
        clientPhoto = (de.hdodenhof.circleimageview.CircleImageView)itemView.findViewById(R.id.client_circleView);
    }
}
