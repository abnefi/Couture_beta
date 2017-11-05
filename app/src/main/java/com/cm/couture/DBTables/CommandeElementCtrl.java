package com.cm.couture.DBTables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cm.couture.commandes.Commande;
import com.cm.couture.commandes.CommandeElement;
import com.cm.couture.main.Utils;

import java.util.ArrayList;
import java.util.List;

public class CommandeElementCtrl {

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public CommandeElementCtrl(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    public void close() {
        db = DBHelper.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }


    public long insertCommandeElement(long commande, String model, String imagePagne, String imageModele, float prix, String observation) {
        long result;
        db = DBHelper.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(Utils.KEY_COMMANDE, commande);
        args.put(Utils.KEY_MODEL, model);
        args.put(Utils.KEY_IMAGE_PAGNE, imagePagne);
        args.put(Utils.KEY_IMAGE_MODELE, imageModele);
        args.put(Utils.KEY_PRIX, prix);
        args.put(Utils.KEY_OBSERVATION,observation);
        result= db.insert(Utils.TABLE_COMMANDE_ELEMENT, null, args);
        close();
        return  result;
    }

    public boolean updateCommandeElement(long rowId, long commande, String model, String imagePagne, String imageModele, float prix, String observation) {
        long result;
        db = DBHelper.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(Utils.KEY_COMMANDE, commande);
        args.put(Utils.KEY_MODEL, model);
        args.put(Utils.KEY_IMAGE_PAGNE, imagePagne);
        args.put(Utils.KEY_IMAGE_MODELE, imageModele);
        args.put(Utils.KEY_PRIX, prix);
        args.put(Utils.KEY_OBSERVATION,observation);
        result = db.update(Utils.TABLE_COMMANDE_ELEMENT, args, Utils.KEY_ID + "=" + rowId, null);
        close();
        return result>0;
    }

    public boolean deleteCommande(long rowId) {
        long result;
        db = DBHelper.getReadableDatabase();
        result=db.delete(Utils.TABLE_COMMANDE_ELEMENT, Utils.KEY_ID + "=" + rowId, null);
        close();
        return result>0;
    }


    public List<CommandeElement> getAllCommandeElement(long commande) {
        List<CommandeElement> commandesElement = new ArrayList<CommandeElement>();
        String selectQuery = "SELECT  * FROM " + Utils.TABLE_COMMANDE+" WHERE "+Utils.KEY_COMMANDE+ " = "+commande ;
        db = DBHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                CommandeElement commandeElement = new CommandeElement();
                commandeElement.setId(c.getLong((c.getColumnIndex(Utils.KEY_ID))));
                commandeElement.setCommande((c.getLong(c.getColumnIndex(Utils.KEY_COMMANDE))));
                commandeElement.setModel(c.getString(c.getColumnIndex(Utils.KEY_MODEL)));
                commandeElement.setImagePagne(c.getString(c.getColumnIndex(Utils.KEY_IMAGE_PAGNE)));
                commandeElement.setImageModele(c.getString(c.getColumnIndex(Utils.KEY_IMAGE_MODELE)));
                commandeElement.setPrix(c.getFloat(c.getColumnIndex(Utils.KEY_PRIX)));
                commandeElement.setObservation(c.getString(c.getColumnIndex(Utils.KEY_OBSERVATION)));

                commandesElement.add(commandeElement);
            } while (c.moveToNext());
        }
        close();
        return commandesElement;
    }

    public CommandeElement getCommandeElement(long rowId){
        db = DBHelper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + Utils.TABLE_COMMANDE_ELEMENT+ " WHERE " + Utils.KEY_ID + " = " + rowId;
        Cursor c = db.rawQuery(selectQuery, null);
        CommandeElement commandeElement = new CommandeElement();

        if (c != null){
            c.moveToFirst();
            commandeElement.setId(c.getLong((c.getColumnIndex(Utils.KEY_ID))));
            commandeElement.setCommande((c.getLong(c.getColumnIndex(Utils.KEY_COMMANDE))));
            commandeElement.setModel(c.getString(c.getColumnIndex(Utils.KEY_MODEL)));
            commandeElement.setImagePagne(c.getString(c.getColumnIndex(Utils.KEY_IMAGE_PAGNE)));
            commandeElement.setImageModele(c.getString(c.getColumnIndex(Utils.KEY_IMAGE_MODELE)));
            commandeElement.setPrix(c.getFloat(c.getColumnIndex(Utils.KEY_PRIX)));
            commandeElement.setObservation(c.getString(c.getColumnIndex(Utils.KEY_OBSERVATION)));
        }
        close();
        return commandeElement;
    }

}