package com.cm.couture.main;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.cm.couture.DBTables.DatabaseHelper;
import com.cm.couture.R;
import com.cm.couture.catalogue.CatalogueFragment;
import com.cm.couture.clients.ClientsFragment;
import com.cm.couture.commandes.CommandeElement;
import com.cm.couture.commandes.CommandeNewFragment;
import com.cm.couture.commandes.CommandesFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    DatabaseHelper db;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            String title = getString(R.string.app_name);

            switch (item.getItemId()) {
                case R.id.navigation_client:
                    fragment = new ClientsFragment();
                    title = getString(R.string.title_client);
                    break;
                case R.id.navigation_commande:
                    fragment = new CommandesFragment();
                    title = getString(R.string.title_commande);
                    break;
                case R.id.navigation_catalogue:
                    fragment = new CatalogueFragment();
                    title = getString(R.string.title_catalogue);
                    break;
                default:
                    break;
            }

            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, fragment);
                fragmentTransaction.commit();
            }

            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Bundle bundle = new Bundle();
        bundle.putLong("ID", 1);
        Fragment fragment = new CommandeNewFragment();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();*/

        //startActivity(new Intent(this, NewClientActivity.class));

        //Data Base
        db = new DatabaseHelper(getApplicationContext());

        //REPOSITORIES
        boolean result=Utils.checkPermission(this);
        if(result) {
            //Directory
            Utils utils=new Utils(getApplicationContext(),this);
            utils.createFolder();
        }

        //Bottom menu
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_commande);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu, menu);
        //final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        //SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
