package com.cm.couture.clients;

import android.app.Activity;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.LinearLayout;

import com.cm.couture.DBTables.ClientCtrl;
import com.cm.couture.R;
import com.cm.couture.commandes.CommandeNewFragment;
import com.cm.couture.main.Utils;

import java.util.List;

import static android.content.Context.SEARCH_SERVICE;


public class ClientsFragment extends Fragment {

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    private RecyclerView.LayoutManager lLayout;
    private Toolbar mToolbar;
    private Utils utils;
    private List<Client> rowListItem;
    private int choix = -1;

    public ClientsFragment() {
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

        utils = new Utils(getContext(), getActivity());

        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar_clients);
        if (mToolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            ((AppCompatActivity) getActivity()).getDelegate().setSupportActionBar(mToolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            mToolbar.setTitle("Clients");
        }

        rowListItem = (new ClientCtrl(rootView.getContext())).getAllClient();
        if (rowListItem.size() == 0) {
            AlertDialog.Builder alert = new AlertDialog.Builder(rootView.getContext());
            alert.setTitle("Client");
            alert.setMessage("Vous n'avez aucun client pour l'instant. Créer des clients grâce au boutton + en haut");
            alert.setPositiveButton("OK", null);
            alert.show();
        }
        lLayout = new LinearLayoutManager(getActivity());

        RecyclerView rView = (RecyclerView) rootView.findViewById(R.id.recycler_view_client);
        rView.setLayoutManager(lLayout);

        RecyclerClientsViewAdapter rcAdapter = new RecyclerClientsViewAdapter(rootView.getContext(), rowListItem);
        rView.setAdapter(rcAdapter);

        rView.addOnItemTouchListener(new RecyclerClientsTouchListener(getContext(), rView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                choix = position;
                de.hdodenhof.circleimageview.CircleImageView img = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.client_circleView);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), FullClientPicture.class);
                        i.putExtra("IMAGE", rowListItem.get(choix).getImage());
                        getActivity().startActivity(i);
                    }
                });

                LinearLayout ll = (LinearLayout) view.findViewById(R.id.zone);
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Bundle bundle = new Bundle();
                        bundle.putLong("ID", rowListItem.get(choix).getId());
                        AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                        alert.setTitle("Opérations sur le client");
                        alert.setMessage("Vous pouvez soit modifier les données du clients ou soit enregistrer une nouvelle commande pour ce client.");
                        alert.setPositiveButton("Nouvelle Commande", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Fragment fragment = new CommandeNewFragment();
                                if (fragment != null) {
                                    fragment.setArguments(bundle);
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.content, fragment);
                                    fragmentTransaction.commit();
                                }

                            }
                        });
                        alert.setNegativeButton("Modifier", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Fragment fragment = new ClientNewFragment();
                                if (fragment != null) {
                                    fragment.setArguments(bundle);
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.content, fragment);
                                    fragmentTransaction.commit();
                                }

                            }
                        });
                        alert.show();
                    }
                });

            }

            @Override
            public void onLongClick(View view, int position) {
                choix = position;
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setTitle("Suppression");
                alert.setMessage("Voulez-vous vraiment supprimer ce client ?");
                alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ClientCtrl clientCtrl = new ClientCtrl(getContext());
                        Client client = rowListItem.get(choix);
                        clientCtrl.deleteClient(client.getId());
                        utils.toast("Client supprimé");
                    }
                });
                alert.setNegativeButton("Non", null);
                alert.show();
            }
        }));

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_client, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search_client));
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_client:
                addClient();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addClient() {

        Bundle bundle = new Bundle();
        bundle.putLong("ID", 0);
        Fragment fragment = new ClientNewFragment();
        if (fragment != null) {
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content, fragment);
            fragmentTransaction.commit();
        }

        //startActivity(new Intent(this.getActivity(), NewClientActivity.class));
        //final Dialog dialog = new Dialog(getContext());
        //dialog.setContentView(R.layout.new_client);
        //dialog.setTitle("Ajouter un nouveau client...");

       /* Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/

        //dialog.show();

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