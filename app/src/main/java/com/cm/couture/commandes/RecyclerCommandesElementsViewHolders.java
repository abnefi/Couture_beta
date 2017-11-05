package com.cm.couture.commandes;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cm.couture.R;

public class RecyclerCommandesElementsViewHolders extends RecyclerView.ViewHolder{

    public TextView model=null;
    public de.hdodenhof.circleimageview.CircleImageView pagne=null;

    public RecyclerCommandesElementsViewHolders(View itemView) {
        super(itemView);

        model = (TextView)itemView.findViewById(R.id.model);
        pagne =(de.hdodenhof.circleimageview.CircleImageView) itemView.findViewById(R.id.imagetenue);
    }
}
