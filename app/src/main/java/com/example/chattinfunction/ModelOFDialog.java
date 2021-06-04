package com.example.chattinfunction;

import com.example.chattinfunction.commons.models.IDialog;

import java.util.ArrayList;

public class ModelOFDialog implements IDialog<ModelOFMessage> {
    String id;
    String DialogPhoto;
    String DialogName;
    ArrayList<ModelOFUser> users;
    ModelOFMessage lastMessage;

    int unreadCount;

    public ModelOFDialog(String id, String Name,String Photo,ArrayList<ModelOFUser> users,ModelOFMessage lastMessage,int unreadCount){
        this.id =id;
        DialogName = Name;
        DialogPhoto = Photo;
        this.users = users;
        this.lastMessage = lastMessage;
        this.unreadCount = unreadCount;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDialogPhoto() {
        return DialogPhoto;
    }

    @Override
    public String getDialogName() {
        return DialogName;
    }

    @Override
    public ArrayList<ModelOFUser> getUsers() {
        return users;
    }

    @Override
    public ModelOFMessage getLastMessage() {
        return lastMessage;
    }

    @Override
    public void setLastMessage(ModelOFMessage message) {
        message = lastMessage;
    }

    @Override
    public int getUnreadCount() {
        return unreadCount;
    }
}
