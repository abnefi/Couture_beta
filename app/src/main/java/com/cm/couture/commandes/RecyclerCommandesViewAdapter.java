package com.cm.couture.commandes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cm.couture.DBTables.ClientCtrl;
import com.cm.couture.R;
import com.cm.couture.clients.Client;
import com.cm.couture.main.Utils;

import java.util.List;

public class RecyclerCommandesViewAdapter extends RecyclerView.Adapter<RecyclerCommandesViewHolders> {

    private List<Commande> itemList;
    private Context context;

    public RecyclerCommandesViewAdapter(Context context, List<Commande> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerCommandesViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_commande, null);
        RecyclerCommandesViewHolders rcv = new RecyclerCommandesViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerCommandesViewHolders holder, int position) {
        Client client =(new ClientCtrl(context)).getClient(itemList.get(position).getClient());
        float reste = itemList.get(position).getMontant()-itemList.get(position).getAvance();
        holder.clientNom.setText(client.getNom());
        holder.clientTelephone.setText("R : "+reste);
        holder.date.setText(itemList.get(position).getDatelivraison());
        //holder.clientPhoto.setImageResource(client.getImage());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
