package com.example.chattinfunction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.example.chattinfunction.commons.ImageLoader;
import com.example.chattinfunction.messages.MessageHolders;
import com.example.chattinfunction.messages.MessageInput;
import com.example.chattinfunction.messages.MessagesList;
import com.example.chattinfunction.messages.MessagesListAdapter;

import java.util.ArrayList;
import java.util.Date;

public class ActivityMainMessage extends AppCompatActivity implements MessagesListAdapter.OnLoadMoreListener, MessageInput.AttachmentsListener,MessageInput.InputListener {

    private static final int TOTAL_MESSAGES_COUNT = 100;

    protected final String senderId = "0";
    protected ImageLoader imageLoader;
    protected MessagesListAdapter<ModelOFMessage> messagesAdapter;
    protected MessagesList messagesList;
    protected String url;
    protected Date lastLoadedDate;
    ImageView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_message_gg);
        view = findViewById(R.id.image);
        url = "https://cdn.pixabay.com/photo/2017/12/25/17/48/waters-3038803_1280.jpg";
        messagesList =findViewById(R.id.messagesList);
        imageLoader = (imageView,url,payload) -> Picasso.get().load(url).into(imageView);

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
    @Override
    public void onStart(){
        super.onStart();
        messagesAdapter.addToStart(FixtureofMessages.getTextMessage(),true);
    }


}