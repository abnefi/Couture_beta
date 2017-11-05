package com.cm.couture.commandes;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cm.couture.R;

public class RecyclerCommandesViewHolders extends RecyclerView.ViewHolder{

    public TextView clientNom=null;
    public TextView clientTelephone=null;
    //public TextView montant=null;
    public TextView date=null;
    public ImageView clientPhoto=null;

    public RecyclerCommandesViewHolders(View itemView) {
        super(itemView);

        clientNom = (TextView)itemView.findViewById(R.id.client_name);
        clientTelephone = (TextView)itemView.findViewById(R.id.telephone);
        //montant = (TextView)itemView.findViewById(R.id.montant);
        date = (TextView)itemView.findViewById(R.id.date);
        clientPhoto = (ImageView)itemView.findViewById(R.id.circleView);
    }
}
