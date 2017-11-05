package com.cm.couture.DBTables;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cm.couture.main.Utils;

/**
 * Created by maurice on 28/07/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";



    // Table Create Statements
    private static final String CREATE_CLIENT =
            "create table " + Utils.TABLE_CLIENT + " ( "+
                    Utils.KEY_ID+" integer primary key autoincrement, "+
                    Utils.KEY_NOM+" VARCHAR(100) not null, "+
                    Utils.KEY_TELEPHONE+" VARCHAR(20), "+
                    Utils.KEY_IMAGE+" VARCHAR(20), "+
                    Utils.KEY_SEXE+" CHAR, "+
                    Utils.KEY_TEINT+" VARCHAR(20), "+
                    Utils.KEY_HPOITRINE+" TINYINT default 0, "+
                    Utils.KEY_LGBUSTE+" TINYINT default 0, "+
                    Utils.KEY_LGCORSAGE+" TINYINT default 0, "+
                    Utils.KEY_LGROBE+" TINYINT default 0, "+
                    Utils.KEY_ENCOLURE+" TINYINT default 0, "+
                    Utils.KEY_CADEVANT+" TINYINT default 0, "+
                    Utils.KEY_TRPOITRINE+" TINYINT default 0, "+
                    Utils.KEY_ECARTSEIN+" TINYINT default 0, "+
                    Utils.KEY_TRTAILLE+" TINYINT default 0, "+
                    Utils.KEY_TRBASSIN+" TINYINT default 0, "+
                    Utils.KEY_LGJUPE+" TINYINT default 0, "+
                    Utils.KEY_LGPANTALON+" TINYINT default 0, "+
                    Utils.KEY_LGDOS+" TINYINT default 0, "+
                    Utils.KEY_CADOS+" TINYINT default 0, "+
                    Utils.KEY_LARGDOS+" TINYINT default 0, "+
                    Utils.KEY_LGMANCHE+" TINYINT default 0, "+
                    Utils.KEY_TRMANCHE+" TINYINT default 0, "+
                    Utils.KEY_POIGNET+" TINYINT default 0, "+
                    Utils.KEY_PENTE+" TINYINT default 0, "+
                    Utils.KEY_OBSERVATION+" TEXT default NULL, "+
                    Utils.KEY_CREATED_AT+" TIMESTAMP default CURRENT_TIMESTAMP);";

    private static final String CREATE_COMMANDE =
            "create table " + Utils.TABLE_COMMANDE + " ("+
                    Utils.KEY_ID+" integer primary key autoincrement, "+
                    Utils.KEY_CLIENT+" integer not null, "+
                    Utils.KEY_MONTANT+" float DEFAULT 0, "+
                    Utils.KEY_AVANCE+" float DEFAULT 0, "+
                    Utils.KEY_DATE_COMMANDE+" TIMESTAMP default CURRENT_TIMESTAMP, "+
                    Utils.KEY_DATE_LIVRAISON+" TIMESTAMP DEFAULT NULL, "+
                    Utils.KEY_DATE_LIVREE+" TIMESTAMP DEFAULT NULL,"+
                    Utils.KEY_CREATED_AT+" TIMESTAMP default CURRENT_TIMESTAMP);";

    private static final String CREATE_COMMANDE_ELEMENT=
            "create table " + Utils.TABLE_COMMANDE_ELEMENT + " ("+
                    Utils.KEY_ID+" integer primary key autoincrement, "+
                    Utils.KEY_COMMANDE+" integer not null, "+
                    Utils.KEY_MODEL+" VARCHAR(50), "+
                    Utils.KEY_IMAGE_PAGNE+" VARCHAR(20) DEFAULT NULL, "+
                    Utils.KEY_IMAGE_MODELE+" VARCHAR(20) DEFAULT NULL, "+
                    Utils.KEY_PRIX+" float DEFAULT 0, "+
                    Utils.KEY_OBSERVATION+" VARCHAR(255) DEFAULT NULL, "+
                    Utils.KEY_CREATED_AT+" TIMESTAMP default CURRENT_TIMESTAMP);";


    public DatabaseHelper(Context context) {
        super(context, Utils.DATABASE_NAME, null, Utils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CLIENT);
        db.execSQL(CREATE_COMMANDE);
        db.execSQL(CREATE_COMMANDE_ELEMENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_CLIENT);
        db.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_COMMANDE);
        db.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_COMMANDE_ELEMENT);

        // create new tables
        onCreate(db);

    }

}
