package com.cm.couture.main;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by maurice on 27/07/2017.
 */

public class Utils {

    //DATA BASE
    // Database Version
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "CoutureDB";

    // Table Names
    public static final String TABLE_CLIENT = "clients";
    public static final String TABLE_COMMANDE = "commandes";
    public static final String TABLE_COMMANDE_ELEMENT = "commandes_elements";

    // Common column names
    public static final String KEY_ID = "id";
    public static final String KEY_CREATED_AT = "created_at";

    // COLONNES CLIENTS
    public static final String KEY_NOM = "nom";
    public static final String KEY_TELEPHONE = "telephone";
    public static final String KEY_SEXE = "sexe";
    public static final String KEY_TEINT = "teint";
    public static final String KEY_HPOITRINE = "hpoitrine";
    public static final String KEY_LGBUSTE = "lgbuste";
    public static final String KEY_LGCORSAGE = "lgcorsage";
    public static final String KEY_LGROBE = "lgrobe";
    public static final String KEY_ENCOLURE = "encolure";
    public static final String KEY_CADEVANT = "cadevant";
    public static final String KEY_TRPOITRINE = "trpoitrine";
    public static final String KEY_ECARTSEIN = "ecartsein";
    public static final String KEY_TRTAILLE = "trtaille";
    public static final String KEY_TRBASSIN = "trbassin";
    public static final String KEY_LGJUPE = "lgjupe";
    public static final String KEY_LGPANTALON = "lgpantalon";
    public static final String KEY_LGDOS = "lgdos";
    public static final String KEY_CADOS = "cados";
    public static final String KEY_LARGDOS = "largdos";
    public static final String KEY_LGMANCHE = "lgmanche";
    public static final String KEY_TRMANCHE = "trmanche";
    public static final String KEY_POIGNET = "poignet";
    public static final String KEY_PENTE = "pente";
    public static final String KEY_OBSERVATION = "observation";


    // COLONNES COMMANDES
    public static final String KEY_CLIENT = "client";
    public static final String KEY_MONTANT = "montant";
    public static final String KEY_AVANCE = "avance";
    public static final String KEY_DATE_COMMANDE = "datecommande";
    public static final String KEY_DATE_LIVRAISON = "datelivraison";
    public static final String KEY_DATE_LIVREE = "datelivree";

    // COLONNES COMMANDES ELEMENTS
    public static final String KEY_COMMANDE = "commande";
    public static final String KEY_MODEL = "model";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_PRIX = "prix";
    public static final String KEY_IMAGE_PAGNE = "imagepagne";
    public static final String KEY_IMAGE_MODELE = "imagemodele";

    // Number of columns of Grid View
    public static final int NUM_OF_COLUMNS = 3;

    // Gridview image padding
    public static final int GRID_PADDING = 8; // in dp

    // SD card image directory
    public static final File PHOTO_DIRECTORY = new File(Environment.getExternalStorageDirectory(),"CoutureApp");
    public static final File CLIENT_DIRECTORY = new File(Environment.getExternalStorageDirectory(),"CoutureApp/Clients");
    public static final File COMMANDE_DIRECTORY = new File(Environment.getExternalStorageDirectory(),"CoutureApp/Commandes");
    public static final File CATALOGUE_DIRECTORY = new File(Environment.getExternalStorageDirectory(),"CoutureApp/Catalogue");
    public static final File TISSU_DIRECTORY = new File(Environment.getExternalStorageDirectory(),"CoutureApp/Tissus");

    // supported file formats
    public static final List<String> FILE_EXTN = Arrays.asList("jpg", "jpeg", "png");

    private Context _context;

    private Activity _activity;

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    // constructor
    public Utils(Context context,Activity activity) {
        this._context = context;
        this._activity=activity;
    }

    // Reading file paths from SDCard
    public ArrayList<String> getFilePaths() {
        ArrayList<String> filePaths = new ArrayList<String>();

        boolean result=checkPermission(_activity);

        if(result) {

            // check for directory
            if (CATALOGUE_DIRECTORY.isDirectory()) {
                // getting list of file paths
                File[] listFiles = CATALOGUE_DIRECTORY.listFiles();

                // Check for count
                if (listFiles.length > 0) {

                    // loop through all files
                    for (int i = 0; i < listFiles.length; i++) {

                        // get file path
                        String filePath = listFiles[i].getAbsolutePath();

                        // check for supported file extension
                        if (IsSupportedFile(filePath)) {
                            // Add image path to array list
                            filePaths.add(filePath);
                        }
                    }
                } else {
                    // image directory is empty
                    //toast("Catalogue vide");
                }

            } else {
                CATALOGUE_DIRECTORY.mkdirs();
                //toast("Catalogue vide");
                /*AlertDialog.Builder alert = new AlertDialog.Builder(_context);
                alert.setTitle("Error!");
                alert.setMessage(PHOTO_ALBUM
                        + " directory path is not valid! Please set the image directory name");
                alert.setPositiveButton("OK", null);
                alert.show();*/
            }
        }

        return filePaths;
    }

    // Check supported file extensions
    private boolean IsSupportedFile(String filePath) {
        String ext = filePath.substring((filePath.lastIndexOf(".") + 1),
                filePath.length());

        if (FILE_EXTN.contains(ext.toLowerCase(Locale.getDefault())))
            return true;
        else
            return false;

    }

    /*
     * getting screen width
     */
    public int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) _context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context)
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    public void createFolder(){
        if ((!PHOTO_DIRECTORY.exists()) || (!PHOTO_DIRECTORY.isDirectory()))
            PHOTO_DIRECTORY.mkdirs();
        if ((!CATALOGUE_DIRECTORY.exists()) || (!CATALOGUE_DIRECTORY.isDirectory()))
            CATALOGUE_DIRECTORY.mkdirs();
        if ((!COMMANDE_DIRECTORY.exists()) || (!COMMANDE_DIRECTORY.isDirectory()))
            COMMANDE_DIRECTORY.mkdirs();
        if ((!CLIENT_DIRECTORY.exists()) || (!CLIENT_DIRECTORY.isDirectory()))
            CLIENT_DIRECTORY.mkdirs();
        if ((!TISSU_DIRECTORY.exists()) || (!TISSU_DIRECTORY.isDirectory()))
            TISSU_DIRECTORY.mkdirs();
        }


    public  void toast(String str){
        Toast.makeText(
                _context, str,
                Toast.LENGTH_LONG).show();
    }

    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {

            File database=_context.getDatabasePath(DATABASE_NAME);

            if (database.exists()) {

                Log.i("Database", "Found");

                String myPath = database.getAbsolutePath();

                Log.i("Database Path", myPath);

                checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

            } else {
                // Database does not exist so copy it from assets here
                Log.i("Database", "Not Found");

            }

        } catch(SQLiteException e) {

            Log.i("Database", "Not Found");

        } finally {

            if(checkDB != null) {

                checkDB.close();

            }

        }

        return checkDB != null ? true : false;
    }

    public String getRealPathFromURI(Uri contentUri,Context context) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = context.getContentResolver().query(
                contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    public static String priceToString(float price) {
        String p = String.valueOf(price);
        String[] split = (String.valueOf(p)).split("\\.");
        long dec= Long.parseLong(split[1]);
        if(dec>0){
            return String.format("%.2f", price);
        }else{
            return String.format("%.0f", price);
        }
    }


}
