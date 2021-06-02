package com.example.chattinfunction;

import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.ArrayList;
import java.util.List;

public class ModelOFDialog implements IDialog<ModelOFMessage> {
    String id;
    String DialogPhotu;
    String DialogName;
    ArrayList<ModelOFUser> users;
    ModelOFMessage lastMessage;

    int unreadCOunt;

    public ModelOFDialog(String id, String Name,String Photo,ArrayList<ModelOFUser> users,ModelOFMessage lastMessage,int unreadCOunt){
        this.id =id;
        DialogName = Name;
        DialogPhotu = Photo;
        this.users = users;
        this.lastMessage = lastMessage;
        this.unreadCOunt = unreadCOunt;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDialogPhoto() {
        return DialogPhotu;
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
        return unreadCOunt;
    }
}
