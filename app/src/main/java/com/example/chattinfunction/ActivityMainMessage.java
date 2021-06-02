package com.example.chattinfunction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageHolders;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.ArrayList;
import java.util.Date;

public class ActivityMainMessage extends AppCompatActivity implements MessagesListAdapter.OnLoadMoreListener, MessageInput.AttachmentsListener,MessageInput.InputListener {

    private static final int TOTAL_MESSAGES_COUNT = 100;

    protected final String senderId = "0";
    protected ImageLoader imageLoader;
    protected MessagesListAdapter<ModelOFMessage> messagesAdapter;
    protected MessagesList messagesList;
    protected Date lastLoadedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_message_gg);
        messagesList =findViewById(R.id.messageList);
        activateAdapter();
        MessageInput input  =findViewById(R.id.input);
        input.setInputListener(this);
        input.setAttachmentsListener(this);
    }

    private void activateAdapter() {
        TextIncViewHolder.Payload payload = new TextIncViewHolder.Payload();
        MessageHolders holdersConfig = new MessageHolders()
                .setIncomingTextConfig(
                        TextIncViewHolder.class,
                        R.layout.item_inc_txt,
                        payload)
                .setOutcomingTextConfig(
                        TextOutViewHolder.class,
                        R.layout.item_out_txt)
                .setIncomingImageConfig(
                       ImgIncViewHolder.class,
                        R.layout.item_inc_img)
                .setOutcomingImageConfig(
                        ImgOutViewHolder.class,
                        R.layout.item_out_img);
    messagesAdapter = new MessagesListAdapter<>(senderId,holdersConfig,imageLoader);
    messagesAdapter.setLoadMoreListener(this);
    messagesList.setAdapter(messagesAdapter);

    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ActivityMainMessage.class);
       // starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    public void onLoadMore(int page, int totalItemsCount) {
        if(totalItemsCount < TOTAL_MESSAGES_COUNT){
            loadMEssage();
        }
    }

    private void loadMEssage() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                ArrayList<ModelOFMessage> messages = FixtureofMessages.getMessages(lastLoadedDate);
        lastLoadedDate = messages.get(messages.size() - 1).getCreatedAt();
        messagesAdapter.addToEnd(messages, false);

        },1000);
    }

    @Override
    public void onAddAttachments() {
        messagesAdapter.addToStart(FixtureofMessages.getImageMessage(),true);
    }

    @Override
    public boolean onSubmit(CharSequence input) {
        messagesAdapter.addToStart(FixtureofMessages.getTextMessage(input.toString()),true);
        return true;
    }
}