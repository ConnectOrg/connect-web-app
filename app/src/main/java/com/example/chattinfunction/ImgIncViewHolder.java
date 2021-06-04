package com.example.chattinfunction;

import android.view.View;

import com.example.chattinfunction.messages.MessageHolders;

public class ImgIncViewHolder  extends MessageHolders.IncomingImageMessageViewHolder<ModelOFMessage> {
    public View indicator;
    public ImgIncViewHolder(View itemView, Object payload) {
        super(itemView, payload);
        indicator = itemView.findViewById(R.id.onlineIndicator);
    }

    @Override
    public void onBind(ModelOFMessage modelOFMessage) {
        super.onBind(modelOFMessage);
        boolean online = modelOFMessage.getUser().isOnline();

        if (online){
            indicator.setBackgroundResource(R.drawable.bubble_shape_onilne_gg);

        }else{
            indicator.setBackgroundResource(R.drawable.bubble_shape_offilne_gg);
        }
    }
}
