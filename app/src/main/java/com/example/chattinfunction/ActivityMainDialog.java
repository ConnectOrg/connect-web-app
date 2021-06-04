package com.example.chattinfunction;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

public class ActivityMainDialog extends AppCompatActivity implements DialogsListAdapter.OnDialogClickListener<ModelOFDialog> {

    DialogsList dialogsList;
    DialogsListAdapter dialogsListAdapter;
    ImageLoader imageLoadergg;
    ImageView imageView;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_main_gg);
        dialogsList = findViewById(R.id.dialogsList);
        imageView = findViewById(R.id.dialogAvatar);
        url = "https://picsum.photos/200/300";
        imageLoadergg = ((imageView, url, payload) -> Picasso.get().load(url).into(imageView));
        adapterActivate();
    }

    private void adapterActivate() {
        dialogsListAdapter = new DialogsListAdapter(
                R.layout.item_view_dialog_gg,
                ViewHolderDialogActivity.class,
                imageLoadergg
        );

        dialogsListAdapter.setOnDialogClickListener(this);
        dialogsListAdapter.setItems(FixtureOFDialogs.getDialogs());
        dialogsList.setAdapter(dialogsListAdapter);
    }


    @Override
    public void onDialogClick(ModelOFDialog dialog) {
        ActivityMainMessage.start(this);
    }
}

