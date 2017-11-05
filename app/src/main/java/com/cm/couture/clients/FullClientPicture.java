package com.cm.couture.clients;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.cm.couture.R;
import com.cm.couture.catalogue.TouchImageView;
import com.cm.couture.main.Utils;

import java.io.File;

public class FullClientPicture extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_client_picture);

        Intent i = getIntent();
        String image = i.getStringExtra("IMAGE");
        String imagePath=(new File(Utils.CLIENT_DIRECTORY,image)).getAbsolutePath();

        TouchImageView imgDisplay;
        Button btnClose;

        imgDisplay = (TouchImageView) findViewById(R.id.imgDisplay);
        btnClose = (Button) findViewById(R.id.btnClose);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
        imgDisplay.setImageBitmap(bitmap);

        // close button click event
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
