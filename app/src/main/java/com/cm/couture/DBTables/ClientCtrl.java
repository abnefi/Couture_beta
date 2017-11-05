package com.cm.couture.DBTables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cm.couture.clients.Client;
import com.cm.couture.commandes.CommandeElement;
import com.cm.couture.main.Utils;

import java.util.ArrayList;
import java.util.List;

public class ClientCtrl {

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public ClientCtrl(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    public void close() {
        db = DBHelper.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }


    public long insertClient(String nom, String telephone, String teint, String image, String sexe, int hpoitrine, int lgbuste, int lgcorsage, int lgrobe, int encolure, int cadevant, int trpoitrine, int ecartsein, int trtaille, int trbassin, int lgjupe, int lgpantalon, int lgdos, int cados, int largdos, int lgmanche, int trmanche, int poignet, int pente, String observation) {
        long result;
        db = DBHelper.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(Utils.KEY_NOM,nom);
        args.put(Utils.KEY_TELEPHONE,telephone);
        args.put(Utils.KEY_IMAGE,image);
        args.put(Utils.KEY_SEXE,sexe);
        args.put(Utils.KEY_TEINT,teint);
        args.put(Utils.KEY_HPOITRINE,hpoitrine);
        args.put(Utils.KEY_LGBUSTE,lgbuste);
        args.put(Utils.KEY_LGCORSAGE,lgcorsage);
        args.put(Utils.KEY_LGROBE,lgrobe);
        args.put(Utils.KEY_ENCOLURE,encolure);
        args.put(Utils.KEY_CADEVANT,cadevant);
        args.put(Utils.KEY_TRPOITRINE,trpoitrine);
        args.put(Utils.KEY_ECARTSEIN,ecartsein);
        args.put(Utils.KEY_TRTAILLE,trtaille);
        args.put(Utils.KEY_TRBASSIN,trbassin);
        args.put(Utils.KEY_LGJUPE,lgjupe);
        args.put(Utils.KEY_LGPANTALON,lgpantalon);
        args.put( Utils.KEY_LGDOS,lgdos);
        args.put(Utils.KEY_CADOS,cados);
        args.put(Utils.KEY_LARGDOS,lgdos);
        args.put(Utils.KEY_LGMANCHE,lgmanche);
        args.put(Utils.KEY_TRMANCHE,trmanche);
        args.put(Utils.KEY_POIGNET,poignet);
        args.put(Utils.KEY_PENTE,pente);
        args.put(Utils.KEY_OBSERVATION,observation);
        result= db.insert(Utils.TABLE_CLIENT, null, args);
        close();
        return  result;
    }

    public boolean deleteClient(long rowId) {
        long result;
        db = DBHelper.getReadableDatabase();
        result=db.delete(Utils.TABLE_CLIENT, Utils.KEY_ID + "=" + rowId, null);
        close();
        return result>0;
    }


    public List<Client> getAllClient() {
        List<Client> clients = new ArrayList<Client>();
        String selectQuery = "SELECT  * FROM " + Utils.TABLE_CLIENT + " ORDER BY "+Utils.KEY_NOM;
        db = DBHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Client client = new Client();
                client.setId(c.getLong((c.getColumnIndex(Utils.KEY_ID))));
                client.setNom((c.getString(c.getColumnIndex(Utils.KEY_NOM))));
                client.setTelephone(c.getString(c.getColumnIndex(Utils.KEY_TELEPHONE)));
                client.setTeint(c.getString(c.getColumnIndex(Utils.KEY_TEINT)));
                client.setImage(c.getString(c.getColumnIndex(Utils.KEY_IMAGE)));
                client.setSexe(c.getString(c.getColumnIndex(Utils.KEY_SEXE)));
                client.setHpoitrine(c.getInt(c.getColumnIndex(Utils.KEY_HPOITRINE)));
                client.setLgbuste(c.getInt(c.getColumnIndex(Utils.KEY_LGBUSTE)));
                client.setLgcorsage(c.getInt(c.getColumnIndex(Utils.KEY_LGCORSAGE)));
                client.setLgrobe(c.getInt(c.getColumnIndex(Utils.KEY_LGROBE)));
                client.setEncolure(c.getInt(c.getColumnIndex(Utils.KEY_ENCOLURE)));
                client.setCadevant(c.getInt(c.getColumnIndex(Utils.KEY_CADEVANT)));
                client.setTrpoitrine(c.getInt(c.getColumnIndex(Utils.KEY_TRPOITRINE)));
                client.setEcartsein(c.getInt(c.getColumnIndex(Utils.KEY_ECARTSEIN)));
                client.setTrtaille(c.getInt(c.getColumnIndex(Utils.KEY_TRTAILLE)));
                client.setTrbassin(c.getInt(c.getColumnIndex(Utils.KEY_TRBASSIN)));
                client.setLgjupe(c.getInt(c.getColumnIndex(Utils.KEY_LGJUPE)));
                client.setLgpantalon(c.getInt(c.getColumnIndex(Utils.KEY_LGPANTALON)));
                client.setLgdos(c.getInt(c.getColumnIndex(Utils.KEY_LGDOS)));
                client.setCados(c.getInt(c.getColumnIndex(Utils.KEY_CADOS)));
                client.setLargdos(c.getInt(c.getColumnIndex(Utils.KEY_LARGDOS)));
                client.setLgmanche(c.getInt(c.getColumnIndex(Utils.KEY_LGMANCHE)));
                client.setTrmanche(c.getInt(c.getColumnIndex(Utils.KEY_TRMANCHE)));
                client.setPoignet(c.getInt(c.getColumnIndex(Utils.KEY_POIGNET)));
                client.setPoignet(c.getInt(c.getColumnIndex(Utils.KEY_PENTE)));
                client.setObservation(c.getString(c.getColumnIndex(Utils.KEY_OBSERVATION)));

                clients.add(client);
            } while (c.moveToNext());
        }
        close();
        return clients;
    }

    public Client getClient(long rowId){
        db = DBHelper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + Utils.TABLE_CLIENT+ " WHERE " + Utils.KEY_ID + " = " + rowId;
        Cursor c = db.rawQuery(selectQuery, null);
        Client client = new Client();

        if (c != null){
            c.moveToFirst();
            client.setId(c.getLong((c.getColumnIndex(Utils.KEY_ID))));
            client.setNom((c.getString(c.getColumnIndex(Utils.KEY_NOM))));
            client.setTelephone(c.getString(c.getColumnIndex(Utils.KEY_TELEPHONE)));
            client.setTeint(c.getString(c.getColumnIndex(Utils.KEY_TEINT)));
            client.setImage(c.getString(c.getColumnIndex(Utils.KEY_IMAGE)));
            client.setSexe(c.getString(c.getColumnIndex(Utils.KEY_SEXE)));
            client.setHpoitrine(c.getInt(c.getColumnIndex(Utils.KEY_HPOITRINE)));
            client.setLgbuste(c.getInt(c.getColumnIndex(Utils.KEY_LGBUSTE)));
            client.setLgcorsage(c.getInt(c.getColumnIndex(Utils.KEY_LGCORSAGE)));
            client.setLgrobe(c.getInt(c.getColumnIndex(Utils.KEY_LGROBE)));
            client.setEncolure(c.getInt(c.getColumnIndex(Utils.KEY_ENCOLURE)));
            client.setCadevant(c.getInt(c.getColumnIndex(Utils.KEY_CADEVANT)));
            client.setTrpoitrine(c.getInt(c.getColumnIndex(Utils.KEY_TRPOITRINE)));
            client.setEcartsein(c.getInt(c.getColumnIndex(Utils.KEY_ECARTSEIN)));
            client.setTrtaille(c.getInt(c.getColumnIndex(Utils.KEY_TRTAILLE)));
            client.setTrbassin(c.getInt(c.getColumnIndex(Utils.KEY_TRBASSIN)));
            client.setLgjupe(c.getInt(c.getColumnIndex(Utils.KEY_LGJUPE)));
            client.setLgpantalon(c.getInt(c.getColumnIndex(Utils.KEY_LGPANTALON)));
            client.setLgdos(c.getInt(c.getColumnIndex(Utils.KEY_LGDOS)));
            client.setCados(c.getInt(c.getColumnIndex(Utils.KEY_CADOS)));
            client.setLargdos(c.getInt(c.getColumnIndex(Utils.KEY_LARGDOS)));
            client.setLgmanche(c.getInt(c.getColumnIndex(Utils.KEY_LGMANCHE)));
            client.setTrmanche(c.getInt(c.getColumnIndex(Utils.KEY_TRMANCHE)));
            client.setPoignet(c.getInt(c.getColumnIndex(Utils.KEY_POIGNET)));
            client.setPoignet(c.getInt(c.getColumnIndex(Utils.KEY_PENTE)));
            client.setObservation(c.getString(c.getColumnIndex(Utils.KEY_OBSERVATION)));
        }
        close();
        return client;
    }

    public boolean updateClient(long rowId, String nom, String telephone, String teint, String image, String sexe, int hpoitrine, int lgbuste, int lgcorsage, int lgrobe, int encolure, int cadevant, int trpoitrine, int ecartsein, int trtaille, int trbassin, int lgjupe, int lgpantalon, int lgdos, int cados, int largdos, int lgmanche, int trmanche, int poignet, int pente, String observation) {
        long result;
        db = DBHelper.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(Utils.KEY_NOM,nom);
        args.put(Utils.KEY_TELEPHONE,telephone);
        args.put(Utils.KEY_IMAGE,image);
        args.put(Utils.KEY_SEXE,sexe);
        args.put(Utils.KEY_TEINT,teint);
        args.put(Utils.KEY_HPOITRINE,hpoitrine);
        args.put(Utils.KEY_LGBUSTE,lgbuste);
        args.put(Utils.KEY_LGCORSAGE,lgcorsage);
        args.put(Utils.KEY_LGROBE,lgrobe);
        args.put(Utils.KEY_ENCOLURE,encolure);
        args.put(Utils.KEY_CADEVANT,cadevant);
        args.put(Utils.KEY_TRPOITRINE,trpoitrine);
        args.put(Utils.KEY_ECARTSEIN,ecartsein);
        args.put(Utils.KEY_TRTAILLE,trtaille);
        args.put(Utils.KEY_TRBASSIN,trbassin);
        args.put(Utils.KEY_LGJUPE,lgjupe);
        args.put(Utils.KEY_LGPANTALON,lgpantalon);
        args.put(Utils.KEY_LGDOS,lgdos);
        args.put(Utils.KEY_CADOS,cados);
        args.put(Utils.KEY_LARGDOS,lgdos);
        args.put(Utils.KEY_LGMANCHE,lgmanche);
        args.put(Utils.KEY_TRMANCHE,trmanche);
        args.put(Utils.KEY_POIGNET,poignet);
        args.put(Utils.KEY_PENTE,pente);
        args.put(Utils.KEY_OBSERVATION,observation);

        result = db.update(Utils.TABLE_CLIENT, args, Utils.KEY_ID + "=" + rowId, null);
        close();
        return result>0;
    }

}