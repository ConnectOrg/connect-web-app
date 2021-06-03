package com.example.chattinfunction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.ArrayList;

public class ActivityMainDialog extends AppCompatActivity implements DialogsListAdapter.OnDialogClickListener<ModelOFDialog>, SearchView.OnQueryTextListener {

    ArrayList<ModelOFDialog> dialogsList = new ArrayList<>();
    DialogsList dialogsListview;
    ActivityMainDialogAdapter dialogsListAdapter;
    ImageLoader imageLoadergg;
    ImageView imageView;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_main_gg);
        dialogsListview = findViewById(R.id.dialogsList);
        imageView = findViewById(R.id.dialogAvatar);
        url = "https://picsum.photos/200/300";
        imageLoadergg = ((imageView, url, payload) -> Picasso.get().load(url).into(imageView));
        adapterActivate();
        Log.d("adapter",dialogsListAdapter.toString());
        Log.d("list", dialogsListview.toString());
    }

    private void adapterActivate() {
        dialogsListAdapter = new ActivityMainDialogAdapter(
                R.layout.item_view_dialog_gg,
                ViewHolderDialogActivity.class,
                imageLoadergg
        );

        dialogsListAdapter.setOnDialogClickListener(this);
        dialogsListAdapter.setItems(FixtureOFDialogs.getDialogs());
        dialogsListview.setAdapter(dialogsListAdapter);
    }


    @Override
    public void onDialogClick(ModelOFDialog dialog) {
        ActivityMainMessage.start(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);


        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                dialogsListAdapter.setFilter(dialogsList);
                return true; // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true; // Return true to expand action view
            }
        });
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final ArrayList<ModelOFDialog> filteredModelList = filter(dialogsList, newText);
        dialogsListAdapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private ArrayList<ModelOFDialog> filter(ArrayList<ModelOFDialog> models, String query) {
        query = query.toLowerCase();

        final ArrayList<ModelOFDialog> filteredModelList = new ArrayList<>();
        for (ModelOFDialog model : models) {
            final String text = model.getDialogName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


}

