package com.cm.couture.DBTables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.cm.couture.commandes.Commande;
import com.cm.couture.main.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommandeCtrl {

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public CommandeCtrl(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    public void close() {
        db = DBHelper.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }


    public long insertCommande(long client,float montant,float avance, String livraison) {
        long result;
        db = DBHelper.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(Utils.KEY_CLIENT, client);
        args.put(Utils.KEY_MONTANT, montant);
        args.put(Utils.KEY_AVANCE, avance);
        args.put(Utils.KEY_DATE_LIVRAISON, livraison);
        result= db.insert(Utils.TABLE_COMMANDE, null, args);
        close();
        return  result;
    }

    public boolean deleteCommande(long rowId) {
        long result;
        db = DBHelper.getReadableDatabase();
        result=db.delete(Utils.TABLE_COMMANDE, Utils.KEY_ID + "=" + rowId, null);
        close();
        return result>0;
    }


    public List<Commande> getAllCommande() {
        List<Commande> commandes = new ArrayList<Commande>();
        String selectQuery = "SELECT  * FROM " + Utils.TABLE_COMMANDE;
        db = DBHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Commande commande = new Commande();
                commande.setId(c.getLong((c.getColumnIndex(Utils.KEY_ID))));
                commande.setClient((c.getLong(c.getColumnIndex(Utils.KEY_CLIENT))));
                commande.setMontant((c.getFloat(c.getColumnIndex(Utils.KEY_MONTANT))));
                commande.setAvance((c.getFloat(c.getColumnIndex(Utils.KEY_AVANCE))));
                commande.setDatecommande(c.getString(c.getColumnIndex(Utils.KEY_DATE_COMMANDE)));
                commande.setDatelivraison(c.getString(c.getColumnIndex(Utils.KEY_DATE_LIVRAISON)));
                commande.setLivree(c.getString(c.getColumnIndex(Utils.KEY_DATE_LIVREE)));

                commandes.add(commande);
            } while (c.moveToNext());
        }
        close();
        return commandes;
    }

    public Commande getCommande(long rowId){
        db = DBHelper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + Utils.TABLE_COMMANDE+ " WHERE " + Utils.KEY_ID + " = " + rowId;
        Cursor c = db.rawQuery(selectQuery, null);
        Commande commande = new Commande();

        if (c != null){
            c.moveToFirst();
            commande.setId(c.getLong((c.getColumnIndex(Utils.KEY_ID))));
            commande.setClient((c.getLong(c.getColumnIndex(Utils.KEY_CLIENT))));
            commande.setMontant((c.getFloat(c.getColumnIndex(Utils.KEY_MONTANT))));
            commande.setAvance((c.getFloat(c.getColumnIndex(Utils.KEY_MONTANT))));
            commande.setDatecommande(c.getString(c.getColumnIndex(Utils.KEY_DATE_COMMANDE)));
            commande.setDatecommande(c.getString(c.getColumnIndex(Utils.KEY_DATE_LIVRAISON)));
            commande.setDatecommande(c.getString(c.getColumnIndex(Utils.KEY_DATE_LIVREE)));
        }
        close();
        return commande;
    }

    public boolean livraisonCommande(long rowId) {
        long result;
        db = DBHelper.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(Utils.KEY_DATE_LIVRAISON, "");
        result = db.update(Utils.TABLE_COMMANDE, args, Utils.KEY_ID + "=" + rowId, null);
        close();
        return result>0;
    }

}