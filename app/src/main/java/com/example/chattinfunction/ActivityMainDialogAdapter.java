package com.example.chattinfunction;

import android.widget.Filter;
import android.widget.Filterable;

import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ActivityMainDialogAdapter extends DialogsListAdapter {
    List<ModelOFDialog> dialogList;
    public ActivityMainDialogAdapter(ImageLoader imageLoader) {
        super(imageLoader);
    }

    public ActivityMainDialogAdapter(int itemLayoutId, ImageLoader imageLoader) {
        super(itemLayoutId, imageLoader);
    }

    public ActivityMainDialogAdapter(int itemLayoutId, Class holderClass, ImageLoader imageLoader) {
        super(itemLayoutId, holderClass, imageLoader);
    }

    public void setFilter(ArrayList<ModelOFDialog> Models){
        dialogList = new ArrayList<>();
        dialogList.addAll(Models);
        notifyDataSetChanged();
    }

}
