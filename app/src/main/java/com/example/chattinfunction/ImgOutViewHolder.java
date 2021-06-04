package com.example.chattinfunction;

import android.util.Pair;
import android.view.View;

import com.example.chattinfunction.messages.MessageHolders;

public class ImgOutViewHolder extends MessageHolders.OutcomingImageMessageViewHolder<ModelOFMessage> {

    public ImgOutViewHolder(View itemView, Object payload) {
        super(itemView, payload);
    }

    @Override
    public void onBind(ModelOFMessage modelOFMessage){
        super.onBind(modelOFMessage);
        time.setText(modelOFMessage.getStatus()+" "+time.getText());

    }

    @Override
    protected Object getPayloadForImageLoader(ModelOFMessage modelOFMessage) {
        return new Pair<>(100,100);
    }
}
