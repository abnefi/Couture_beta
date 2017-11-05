package com.cm.couture.clients;

import android.app.Activity;
import android.app.SearchManager;
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

import com.cm.couture.DBTables.ClientCtrl;
import com.cm.couture.R;
import com.cm.couture.commandes.CommandeNewFragment;
import com.cm.couture.main.Utils;

import java.util.List;

import static android.content.Context.SEARCH_SERVICE;


public class ClientsChooseFragment extends Fragment {

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }


    private RecyclerView.LayoutManager  lLayout;
    private Toolbar mToolbar;
    private Utils utils;
    private List<Client> rowListItem;
    private int choix=-1;

    public ClientsChooseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_clients, container, false);

        utils=new Utils(getContext(),getActivity());

        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar_clients);
        if (mToolbar != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
            ((AppCompatActivity) getActivity()).getDelegate().setSupportActionBar(mToolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            mToolbar.setTitle("Choisir un client");
        }

       rowListItem = (new ClientCtrl(rootView.getContext())).getAllClient();
        if(rowListItem.size()==0){
            AlertDialog.Builder alert = new AlertDialog.Builder(rootView.getContext());
            alert.setTitle("Client");
            alert.setMessage("Vous n'avez aucun client pour l'instant. Créer des clients grâce au boutton + en haut dans le menu Client");
            alert.setPositiveButton("OK", null);
            alert.show();
        }
        lLayout = new LinearLayoutManager(getActivity());

        RecyclerView rView = (RecyclerView) rootView.findViewById(R.id.recycler_view_client);
        rView.setLayoutManager(lLayout);

        RecyclerClientsViewAdapter rcAdapter = new RecyclerClientsViewAdapter(rootView.getContext(), rowListItem);
        rView.setAdapter(rcAdapter);

        rView.addOnItemTouchListener(new RecyclerClientsChooseTouchListener(getContext(), rView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                choix =position;
                Bundle bundle = new Bundle();
                bundle.putLong("ID", rowListItem.get(choix).getId());
                Fragment fragment = new CommandeNewFragment();
                    if (fragment != null) {
                            fragment.setArguments(bundle);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.content, fragment);
                            fragmentTransaction.commit();
                        }
            }

            @Override
            public void onLongClick(View view, int position) {
                choix =position;
            }
        }));


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_client_choose, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search_client));
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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