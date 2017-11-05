package com.cm.couture.clients;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cm.couture.R;
import com.cm.couture.main.Utils;

import java.io.File;
import java.util.List;

public class RecyclerClientsViewAdapter extends RecyclerView.Adapter<RecyclerClientsViewHolders> {

    private List<Client> itemList;
    private Context context;

    public RecyclerClientsViewAdapter(Context context, List<Client> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerClientsViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_client, null);
        RecyclerClientsViewHolders rcv = new RecyclerClientsViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerClientsViewHolders holder, int position) {
        final int p=position;
        holder.clientNom.setText(itemList.get(position).getNom());
        holder.clientTelephone.setText(itemList.get(position).getTelephone());

        if(!itemList.get(position).getImage().equals("")){
            File f = new File(Utils.CLIENT_DIRECTORY,itemList.get(position).getImage());
            holder.clientPhoto.setImageURI(Uri.fromFile(f));
        }

        //holder.clientSexe.setText(itemList.get(position).getSexe());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
