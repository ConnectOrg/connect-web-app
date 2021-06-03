package com.example.chattinfunction;

import android.widget.Filter;
import android.widget.Filterable;

import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ActivityMainDialogAdapter extends DialogsListAdapter implements Filterable {
    List<ModelOFDialog> dialogList;
    List<ModelOFDialog> dialogListFiltered;
    public ActivityMainDialogAdapter(ImageLoader imageLoader) {
        super(imageLoader);
    }

    public ActivityMainDialogAdapter(int itemLayoutId, ImageLoader imageLoader) {
        super(itemLayoutId, imageLoader);
    }

    public ActivityMainDialogAdapter(int itemLayoutId, Class holderClass, ImageLoader imageLoader) {
        super(itemLayoutId, holderClass, imageLoader);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    dialogListFiltered = dialogList;
                } else {
                    List<ModelOFDialog> filteredList = new ArrayList<>();
                    for (ModelOFDialog dialog : dialogList) {
                        if (dialog.getDialogName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(dialog);
                        }
                    }
                    dialogListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dialogListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dialogListFiltered = (ArrayList<ModelOFDialog>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }
}
