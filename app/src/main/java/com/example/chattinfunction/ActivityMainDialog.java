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
    ActivityMainDialogAdapter dialogsListAdapter;
    ImageLoader imageLoadergg;
    ImageView imageView;
    String url;
    private SearchView searchView;

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
        dialogsListAdapter = new ActivityMainDialogAdapter(
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialogsListAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                dialogsListAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}

