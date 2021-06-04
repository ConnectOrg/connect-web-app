package com.example.chattinfunction;

import android.view.View;

import com.example.chattinfunction.messages.MessageHolders;

public class TextIncViewHolder extends MessageHolders.IncomingTextMessageViewHolder<ModelOFMessage> {

    private View indicator;

    public TextIncViewHolder(View itemView, Object payload) {
        super(itemView, payload);
        indicator = itemView.findViewById(R.id.onlineIndicator);
    }

    @Override
    public void onBind(ModelOFMessage message) {
        super.onBind(message);

        boolean online = message.getUser().isOnline();
        if (online){
            indicator.setBackgroundResource(R.drawable.bubble_shape_onilne_gg);

        }else{
            indicator.setBackgroundResource(R.drawable.bubble_shape_offilne_gg);
        }

        final Payload payload = (Payload) this.payload;
        userAvatar.setOnClickListener(view ->{
            if(payload != null && payload.avatarClickListener != null){
                payload.avatarClickListener.onAvatarClick()
            ;}
        });
    }

    public static class Payload {
        public onAvatarClickListener avatarClickListener;
    }
    public interface onAvatarClickListener{
        void onAvatarClick();
    }
}
