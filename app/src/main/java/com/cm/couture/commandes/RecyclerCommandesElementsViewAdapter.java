package com.cm.couture.commandes;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cm.couture.DBTables.ClientCtrl;
import com.cm.couture.R;
import com.cm.couture.clients.Client;
import com.cm.couture.main.Utils;

import java.io.File;
import java.util.List;

public class RecyclerCommandesElementsViewAdapter extends RecyclerView.Adapter<RecyclerCommandesElementsViewHolders> {

    private List<CommandeElement> itemList;
    private Context context;

    public RecyclerCommandesElementsViewAdapter(Context context, List<CommandeElement> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerCommandesElementsViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_commande_element, null);
        RecyclerCommandesElementsViewHolders rcv = new RecyclerCommandesElementsViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerCommandesElementsViewHolders holder, int position) {
        holder.model.setText(itemList.get(position).getModel());
        if(!itemList.get(position).getImagePagne().equals("")){
            File f = new File(Utils.TISSU_DIRECTORY,itemList.get(position).getImagePagne());
            holder.pagne.setImageURI(Uri.fromFile(f));
        }
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public float getTotal(){
        float total=0;
        for(int i=0;i<this.itemList.size();i++){
            total+=this.itemList.get(i).getPrix();
        }
        return total;
    }
}
