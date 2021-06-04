package com.example.chattinfunction;

import android.view.View;

import com.example.chattinfunction.messages.MessageHolders;

public class TextOutViewHolder extends MessageHolders.OutcomingTextMessageViewHolder<ModelOFMessage> {
    public TextOutViewHolder(View itemView, Object payload) {
        super(itemView, payload);
    }

    @Override
    public void onBind(ModelOFMessage message) {
        super.onBind(message);

        time.setText(message.getStatus()+"  "+time.getText());
    }
}
