package com.cm.couture.commandes;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cm.couture.DBTables.CommandeCtrl;
import com.cm.couture.R;
import com.cm.couture.clients.ClientsChooseFragment;
import com.cm.couture.main.Utils;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.SEARCH_SERVICE;


public class CommandesFragment extends Fragment {

    private RecyclerView.LayoutManager  lLayout;
    private Toolbar mToolbar;

    public CommandesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_commandes, container, false);

        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar_commandes);
        if (mToolbar != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
            ((AppCompatActivity) getActivity()).getDelegate().setSupportActionBar(mToolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            mToolbar.setTitle("Commandes");
        }

        List<Commande> rowListItem = (new CommandeCtrl(rootView.getContext())).getAllCommande();

        if(rowListItem.size()==0){
            AlertDialog.Builder alert = new AlertDialog.Builder(rootView.getContext());
            alert.setTitle("Commande");
            alert.setMessage("Vous n'avez aucune commande en attente. Créer des commandes à partir de la liste des clients");
            alert.setPositiveButton("OK", null);
            alert.show();
        }
        lLayout = new LinearLayoutManager(getActivity());

        RecyclerView rView = (RecyclerView) rootView.findViewById(R.id.recycler_view_commande);
        rView.setLayoutManager(lLayout);

        RecyclerCommandesViewAdapter rcAdapter = new RecyclerCommandesViewAdapter(rootView.getContext(), rowListItem);
        rView.setAdapter(rcAdapter);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_commande, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search_commande));
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_commande:
                Fragment fragment = new ClientsChooseFragment();
                if (fragment != null) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content, fragment);
                    fragmentTransaction.commit();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}