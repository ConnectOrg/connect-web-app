package com.example.chattinfunction;

import com.example.chattinfunction.commons.models.IUser;

public class ModelOFUser implements IUser {

    String uid;
    String uname;
    String avatar;
    boolean uonline;

    public  ModelOFUser(String id , String name , String avatar , boolean online){
        uid = id;
        uname =name;
        this.avatar = avatar;
        uonline = online;
    }
    @Override
    public String getId() {
        return uid;
    }

    @Override
    public String getName() {
        return uname;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    public boolean isOnline() {
        return uonline;
    }
}
