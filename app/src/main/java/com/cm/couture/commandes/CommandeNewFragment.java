package com.cm.couture.commandes;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.cm.couture.DBTables.ClientCtrl;
import com.cm.couture.DBTables.CommandeCtrl;
import com.cm.couture.DBTables.CommandeElementCtrl;
import com.cm.couture.R;
import com.cm.couture.clients.Client;
import com.cm.couture.clients.ClientsFragment;
import com.cm.couture.main.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommandeNewFragment extends Fragment {

    private Toolbar mToolbar;
    private Menu menu=null;
    private MenuItem menuItemAdd=null;
    Utils utils=null;
    private long idClient=0;
    private Client client=null;
    private RecyclerView.LayoutManager  lLayout;
    List<CommandeElement> rowListItem;
    RecyclerCommandesElementsViewAdapter rcAdapter;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    CommandeElement commandeElement=null;
    String modele = "";
    String date_time = "";
    int mYear;
    int mMonth;
    int mDay;
    int mHour;
    int mMinute;
    Date livraison=null;

    private ImageView imagePagne;
    private ImageView imageModel;
    private Uri pagneUri;
    private Uri modeleUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_commande_new, container, false);
        utils=new Utils(getContext(),getActivity());

        idClient = this.getArguments().getLong("ID");

        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        if (mToolbar != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
            ((AppCompatActivity) getActivity()).getDelegate().setSupportActionBar(mToolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            mToolbar.setTitle("Nouvelle Commande");
        }

        rowListItem= new ArrayList<CommandeElement>();

        lLayout = new LinearLayoutManager(getActivity());

        RecyclerView rView = (RecyclerView) rootView.findViewById(R.id.recycler_view_commande_element);
        rView.setLayoutManager(lLayout);

        rcAdapter = new RecyclerCommandesElementsViewAdapter(rootView.getContext(), rowListItem);
        rView.setAdapter(rcAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        TextView add = (TextView) getActivity().findViewById(R.id.ajouttenue);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newTenue();
            }
        });

        ImageView dateheure = (ImageView) getActivity().findViewById(R.id.dateheureimage);
        dateheure.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                datePicker();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(idClient==0){
            client=new Client();
        }else{
            ClientCtrl clientCtrl=new ClientCtrl(getContext());
            client=clientCtrl.getClient(idClient);
            newCommande(client);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_new_commande, menu);
        this.menu=menu;
        this.menuItemAdd = menu.findItem(R.id.add_commande);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_commande) {
            if(rowListItem.size()==0){
                utils.toast("Vous devez ajouter au moins une tenue");
                menuItemAdd.setVisible(false);
            }else{
                addCommande();
            }
            return true;
        }
        if (id == R.id.close_commande) {
            Fragment fragment = new CommandesFragment();
            if (fragment != null) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, fragment);
                fragmentTransaction.commit();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void newCommande(Client client)
    {
        ((TextView)getView().findViewById(R.id.commande_nom)).setText(client.getNom());
        ((TextView)getView().findViewById(R.id.commande_telephone)).setText(client.getTelephone());
        ((TextView)getView().findViewById(R.id.commande_autre)).setText("Sexe : "+client.getSexe()+" | Teint : "+client.getTeint());
    }

    public void newTenue(){
        commandeElement =new CommandeElement();
        modele ="";
        AlertDialog.Builder builder = new AlertDialog.Builder(getView().getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setTitle("Ajouter une nouvelle tenue");
        builder.setCancelable(true);

        final View dialogView = inflater.inflate(R.layout.layout_tenue, null);

        TextView pagne = (TextView) dialogView.findViewById(R.id.newPagne);
        pagne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newPagne();
            }
        });


        TextView catalogue = (TextView) dialogView.findViewById(R.id.newModel);
        catalogue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newModele();
            }
        });

        imagePagne=(ImageView) dialogView.findViewById(R.id.imagepagne);
        imagePagne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newPagne();
            }
        });
        imageModel=(ImageView) dialogView.findViewById(R.id.imagemodel);
        imageModel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newModele();
            }
        });

        final String[] modeles = getResources().getStringArray(R.array.modeles);
        Spinner s1 = (Spinner) dialogView.findViewById(R.id.model);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, modeles);
        s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0,
                                       View arg1, int arg2, long arg3)
            {
                modele =modeles[arg0.getSelectedItemPosition()];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                modele ="";
            }
        });

        builder.setView(dialogView)
                // Add action buttons
                .setPositiveButton("Enregistrer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        commandeElement.setModel(modele);
                        commandeElement.setObservation(((EditText)dialogView.findViewById(R.id.observation)).getText().toString());
                        commandeElement.setPrix(Float.parseFloat(((EditText)dialogView.findViewById(R.id.prix)).getText().toString()));
                        if(rowListItem.size()==0){
                            menuItemAdd.setVisible(true);
                        }
                        rowListItem.add(commandeElement);
                        rcAdapter.notifyDataSetChanged();
                        mToolbar.setTitle("Nouvelle Commande : "+Utils.priceToString(rcAdapter.getTotal()));

                    }})
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create();
        builder.show();
    }

    public void newPagne(){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);

    }

    public void newModele(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Sélectionner une image"),SELECT_FILE);

        /*String path = Environment.getExternalStorageDirectory()+File.separator+"CoutureApp"+File.separator+"Catalogue/";
        File folder = new File(path);
        String[] allFiles = folder.list();
        if(allFiles!=null && allFiles.length>0) {
            String filePath = path + allFiles[0];
            Cursor cursor = getActivity().getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Images.Media._ID},
                    MediaStore.Images.Media.DATA + "=? ",
                    new String[]{filePath}, null);
            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                cursor.close();
                Intent galleryIntent = new Intent(Intent.ACTION_VIEW, Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id));
                //startActivity(galleryIntent);
                startActivityForResult(Intent.createChooser(galleryIntent, "Sélectionner un modèle"),SELECT_FILE);
                return;
            }
        }*/

    }

    private void addCommande(){
        //Create Commande
        CommandeCtrl commandeCtrl=new CommandeCtrl(getContext());
        String  e = ((EditText)getActivity().findViewById(R.id.avance)).getText().toString();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateLivraison = df.format(livraison);
        float avance=0;
        if(!e.equals("")) avance = Float.parseFloat(e);
        long commande = commandeCtrl.insertCommande(idClient,rcAdapter.getTotal(),avance,dateLivraison);

        //Create Commande élément
        CommandeElementCtrl commandeElementCtrl=new CommandeElementCtrl(getContext());
        for(int i=0; i<rowListItem.size();i++){
            commandeElementCtrl.insertCommandeElement(commande,rowListItem.get(i).getModel(),rowListItem.get(i).getImagePagne(),rowListItem.get(i).getImageModele(),rowListItem.get(i).getPrix(),rowListItem.get(i).getObservation());
        }

        if(commande >0){
            utils.toast("Commande enregistrée avec succès");
            Fragment fragment = new CommandesFragment();
            if (fragment != null) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, fragment);
                fragmentTransaction.commit();
            }
        }else{
            utils.toast("Erreur lors de la création de la commande");
        }
    }

    private void datePicker(){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),R.style.datepicker,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        date_time = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        tiemPicker();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void tiemPicker(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),R.style.datepicker,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;
                        EditText datetime=(EditText)getView().findViewById(R.id.dateheure);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, mYear);
                        calendar.set(Calendar.MONTH, mMonth);
                        calendar.set(Calendar.DATE, mDay);
                        calendar.set(Calendar.HOUR,mHour);
                        calendar.set(Calendar.MINUTE,mMinute);
                        calendar.set(Calendar.SECOND,0);
                        datetime.setText(date_time+" à "+hourOfDay + ":" + minute);
                        livraison = calendar.getTime();
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    private void modifyPicture(ImageView img, Uri uri){
        if (uri != null) {
            img.setImageURI(uri);
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE){
                modeleUri = data.getData();
                String selectedImage = utils.getRealPathFromURI(data.getData(),getContext());
                commandeElement.setImageModele(selectedImage.substring(selectedImage.lastIndexOf(File.separator)+1));
                modifyPicture(imageModel,modeleUri);

                }
            else if (requestCode == REQUEST_CAMERA){
                pagneUri = data.getData();
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                String nom=System.currentTimeMillis() + ".jpg";

                File destination = new File(Utils.CATALOGUE_DIRECTORY,nom);

                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                modifyPicture(imagePagne,pagneUri);
                commandeElement.setImagePagne(nom);
            }
        }
    }
}
