package com.example.chattinfunction;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;

import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

public class ActivityMainDialog extends AppCompatActivity implements DialogsListAdapter.OnDialogClickListener<ModelOFDialog> {

    DialogsList dialogsList;
    DialogsListAdapter<ModelOFDialog> dialogsListAdapter;
    ImageLoader imageLoadergg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_main_gg);
        dialogsList = findViewById(R.id.my_dialog_list);
        imageLoadergg = ((imageView, url, payload) -> Picasso.get().load(url).into(imageView));
        adapterActivate();
    }

    private void adapterActivate() {
        dialogsListAdapter = new DialogsListAdapter<>(
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

