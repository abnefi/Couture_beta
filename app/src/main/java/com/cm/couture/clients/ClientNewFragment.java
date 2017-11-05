package com.cm.couture.clients;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cm.couture.DBTables.ClientCtrl;
import com.cm.couture.R;
import com.cm.couture.catalogue.FullScreenViewActivity;
import com.cm.couture.main.MainActivity;
import com.cm.couture.main.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClientNewFragment extends Fragment {

    private Toolbar mToolbar;
    String[] teints;
    String[] sexes;
    String teint="";
    String sexe="";
    Utils utils=null;
    private Menu menu=null;
    private MenuItem menuItemAdd=null;
    private long idClient=0;
    private String nomImage="";
    private Client client=null;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private de.hdodenhof.circleimageview.CircleImageView image_client;
    private Uri mSelectedImageUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_client_new, container, false);
        utils=new Utils(getContext(),getActivity());

        image_client = (de.hdodenhof.circleimageview.CircleImageView ) rootView.findViewById(R.id.img_client);
        if (mSelectedImageUri != null) {
            image_client.setImageURI(mSelectedImageUri);
        }


        idClient = this.getArguments().getLong("ID");

        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        if (mToolbar != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
            ((AppCompatActivity) getActivity()).getDelegate().setSupportActionBar(mToolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            mToolbar.setTitle("Nouveau Client");
        }

        sexes = getResources().getStringArray(R.array.sexes);
        Spinner s1 = (Spinner) rootView.findViewById(R.id.nom_sexe);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, sexes);
        s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0,
                                       View arg1, int arg2, long arg3)
            {
                sexe=sexes[arg0.getSelectedItemPosition()];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                sexe="";
            }
        });

        teints = getResources().getStringArray(R.array.teints);
        Spinner s2 = (Spinner) rootView.findViewById(R.id.nom_teint);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, teints);
        s2.setAdapter(adapter2);
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0,
                                       View arg1, int arg2, long arg3)
            {
                teint=teints[arg0.getSelectedItemPosition()];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                teint="";
            }
        });




        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        image_client.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final CharSequence[] items = { "Prendre une photo", "Choisir depuis votre téléphone",
                        "Annuler" };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Ajouter un modèle!");
                builder.setItems(items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {
                        boolean result=utils.checkPermission(getActivity());

                        if (items[item].equals("Prendre une photo")) {
                            if(result){
                                cameraIntent();
                            }
                        } else if (items[item].equals("Choisir depuis votre téléphone")) {
                            if(result){
                                galleryIntent();
                            }

                        } else if (items[item].equals("Annuler")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Sélectionner une image"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(idClient==0){
            client=new Client();
        }else{
            mToolbar.setTitle("Modification");
            ClientCtrl clientCtrl=new ClientCtrl(getContext());
            client=clientCtrl.getClient(idClient);
            oldValues(client);
        }

        final EditText nomEditText=((EditText)getView().findViewById(R.id.nom_client));
        nomEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals(" ")||s.toString().equals("")){
                    menuItemAdd.setVisible(false);
                }else{
                    menuItemAdd.setVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(nomEditText.getText().toString().equals(" ")){
                    nomEditText.setText("");
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_new_client, menu);
        this.menu=menu;
        this.menuItemAdd = menu.findItem(R.id.add_client);
        if(idClient>0) menuItemAdd.setVisible(true);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_client) {
            saveClient();
            return true;
        }
        if (id == R.id.close_client) {
            Fragment fragment = new ClientsFragment();
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

    public void oldValues(Client client){
        ((EditText)getView().findViewById(R.id.nom_client)).setText(client.getNom());
        ((EditText)getView().findViewById(R.id.nom_telephone)).setText(client.getTelephone());
        ((EditText)getView().findViewById(R.id.hpoitrine)).setText(retourneString(client.getHpoitrine()));
        ((EditText)getView().findViewById(R.id.lgbuste)).setText(retourneString(client.getLgbuste()));
        ((EditText)getView().findViewById(R.id.lgcorsage)).setText(retourneString(client.getLgcorsage()));
        ((EditText)getView().findViewById(R.id.lgrobe)).setText(retourneString(client.getLgrobe()));
        ((EditText)getView().findViewById(R.id.encolure)).setText(retourneString(client.getEncolure()));
        ((EditText)getView().findViewById(R.id.cadevant)).setText(retourneString(client.getCadevant()));
        ((EditText)getView().findViewById(R.id.trpoitrine)).setText(retourneString(client.getTrpoitrine()));
        ((EditText)getView().findViewById(R.id.ecartsein)).setText(retourneString(client.getEcartsein()));
        ((EditText)getView().findViewById(R.id.trtaille)).setText(retourneString(client.getTrtaille()));
        ((EditText)getView().findViewById(R.id.trbassin)).setText(retourneString(client.getTrbassin()));
        ((EditText)getView().findViewById(R.id.lgjupe)).setText(retourneString(client.getLgjupe()));
        ((EditText)getView().findViewById(R.id.lgpantalon)).setText(retourneString(client.getLgpantalon()));
        ((EditText)getView().findViewById(R.id.lgdos)).setText(retourneString(client.getLgdos()));
        ((EditText)getView().findViewById(R.id.cados)).setText(retourneString(client.getCados()));
        ((EditText)getView().findViewById(R.id.largdos)).setText(retourneString(client.getLargdos()));
        ((EditText)getView().findViewById(R.id.lgmanche)).setText(retourneString(client.getLgmanche()));
        ((EditText)getView().findViewById(R.id.trmanche)).setText(retourneString(client.getTrmanche()));
        ((EditText)getView().findViewById(R.id.poignet)).setText(retourneString(client.getPoignet()));
        ((EditText)getView().findViewById(R.id.pente)).setText(retourneString(client.getPente()));
        ((EditText)getView().findViewById(R.id.observation)).setText(client.getObservation());

        de.hdodenhof.circleimageview.CircleImageView clientPhoto=(de.hdodenhof.circleimageview.CircleImageView)getView().findViewById(R.id.img_client);
        if(!client.getImage().equals("")){
            File f = new File(Utils.CLIENT_DIRECTORY,client.getImage());
            clientPhoto.setImageURI(Uri.fromFile(f));
        }

    }


    public void saveClient(){
        ClientCtrl clientCtrl=new ClientCtrl(getContext());
        String nom=((EditText)getView().findViewById(R.id.nom_client)).getText().toString();
        String telephone=((EditText)getView().findViewById(R.id.nom_telephone)).getText().toString();
        int hpoitrine= retourneInt(((EditText)getView().findViewById(R.id.hpoitrine)).getText().toString());
        int lgbuste= retourneInt(((EditText)getView().findViewById(R.id.lgbuste)).getText().toString());
        int lgcorsage= retourneInt(((EditText)getView().findViewById(R.id.lgcorsage)).getText().toString());
        int lgrobe= retourneInt(((EditText)getView().findViewById(R.id.lgrobe)).getText().toString());
        int encolure= retourneInt(((EditText)getView().findViewById(R.id.encolure)).getText().toString());
        int cadevant= retourneInt(((EditText)getView().findViewById(R.id.cadevant)).getText().toString());
        int trpoitrine= retourneInt(((EditText)getView().findViewById(R.id.trpoitrine)).getText().toString());
        int ecartsein= retourneInt(((EditText)getView().findViewById(R.id.ecartsein)).getText().toString());
        int trtaille= retourneInt(((EditText)getView().findViewById(R.id.trtaille)).getText().toString());
        int trbassin= retourneInt(((EditText)getView().findViewById(R.id.trbassin)).getText().toString());
        int lgjupe= retourneInt(((EditText)getView().findViewById(R.id.lgjupe)).getText().toString());
        int lgpantalon= retourneInt(((EditText)getView().findViewById(R.id.lgpantalon)).getText().toString());
        int lgdos= retourneInt(((EditText)getView().findViewById(R.id.lgdos)).getText().toString());
        int cados= retourneInt(((EditText)getView().findViewById(R.id.cados)).getText().toString());
        int largdos= retourneInt(((EditText)getView().findViewById(R.id.largdos)).getText().toString());
        int lgmanche= retourneInt(((EditText)getView().findViewById(R.id.lgmanche)).getText().toString());
        int trmanche= retourneInt(((EditText)getView().findViewById(R.id.trmanche)).getText().toString());
        int poignet= retourneInt(((EditText)getView().findViewById(R.id.poignet)).getText().toString());
        int pente= retourneInt(((EditText)getView().findViewById(R.id.pente)).getText().toString());
        String observation=((EditText)getView().findViewById(R.id.observation)).getText().toString();

        if(idClient==0){
            long result = clientCtrl.insertClient(nom,telephone,teint,nomImage,sexe,hpoitrine, lgbuste, lgcorsage, lgrobe,  encolure,  cadevant,  trpoitrine,  ecartsein,  trtaille,  trbassin,  lgjupe,  lgpantalon,  lgdos,  cados,  largdos,  lgmanche,  trmanche,  poignet,  pente,  observation);
            if(result >0){
                utils.toast("Client ajouté avec succès");
                Fragment fragment = new ClientsFragment();
                if (fragment != null) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content, fragment);
                    fragmentTransaction.commit();
                }
            }else{
                utils.toast("Erreur lors de l'ajout du client");
            }
        }else{
            boolean result = clientCtrl.updateClient(idClient,nom,telephone,teint,nomImage,sexe,hpoitrine, lgbuste, lgcorsage, lgrobe,  encolure,  cadevant,  trpoitrine,  ecartsein,  trtaille,  trbassin,  lgjupe,  lgpantalon,  lgdos,  cados,  largdos,  lgmanche,  trmanche,  poignet,  pente,  observation);
            if(result){
                utils.toast("Modification réussie");
                Fragment fragment = new ClientsFragment();
                if (fragment != null) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content, fragment);
                    fragmentTransaction.commit();
                }
            }else{
                utils.toast("Erreur lors de la modification du client");
            }
        }


    }

    public int retourneInt(String str){
        if(str.equals("")) {
            return 0;
        } else{
            return Integer.parseInt(str);
        }
    }

    public String retourneString(int val){
        if(val==0) {
            return "";
        } else{
            return val+"";
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            mSelectedImageUri = data.getData();
            if (requestCode == SELECT_FILE){
                onSelectFromGalleryResult(data);
            } else if (requestCode == REQUEST_CAMERA){
                onCaptureImageResult(data);
            }


            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        nomImage=System.currentTimeMillis() + ".jpg";

        File destination = new File(Utils.CLIENT_DIRECTORY,nomImage);

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
            modifyPicture();
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        String selectedImage = utils.getRealPathFromURI(data.getData(),getContext());

        nomImage = System.currentTimeMillis() + selectedImage.substring(selectedImage.lastIndexOf("."));

        File f = new File(utils.CLIENT_DIRECTORY + File.separator + nomImage);
        if (!f.exists())
        {
            try {
                f.createNewFile();
                copyFile(new File(utils.getRealPathFromURI(data.getData(),getContext())), f);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            modifyPicture();
        }

    }

    private void modifyPicture(){
        if (mSelectedImageUri != null) {
            image_client.setImageURI(mSelectedImageUri);
        }

    }

    private void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }

        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }


    }
}
