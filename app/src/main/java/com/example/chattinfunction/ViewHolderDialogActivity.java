package com.example.chattinfunction;

import android.view.View;

import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.example.chattinfunction.ModelOFUser;

public class ViewHolderDialogActivity extends DialogsListAdapter.DialogViewHolder <ModelOFDialog>{
    private View indicator;
    public ViewHolderDialogActivity(View itemView) {
        super(itemView);
        indicator = itemView.findViewById(R.id.indicatorToonline);
    }

    @Override
    public void onBind(ModelOFDialog dialog) {
        super.onBind(dialog);
        if (dialog.getUsers().size() > 1){
            indicator.setVisibility(View.GONE);
        }else{
            boolean online = dialog.getUsers().get(0).isOnline();
            indicator.setVisibility(View.VISIBLE);
            if (online){
                indicator.setBackgroundResource(R.drawable.bubble_shape_offilne_gg);
            }
            else{
                indicator.setBackgroundResource(R.drawable.bubble_shape_onilne_gg);}
        }
    }
}
